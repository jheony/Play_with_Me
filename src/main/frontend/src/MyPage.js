import React, { useState, useEffect, useContext } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import './App.css';
import Modal from './components/Modal';
import './styles/MyPage.css';
import { AuthContext } from './contexts/AuthContext';

function MyPage() {
  const { user } = useContext(AuthContext); // 사용자
  const { res } = useContext(AuthContext); // 예약자
  const [date, setDate] = useState(new Date()); // 날짜
  const [acceptedReservations, setAcceptedReservations] = useState({}); // 받은 일정
  const [pendingRequests, setPendingRequests] = useState([]); // 수락한 일정

  const [showModal, setShowModal] = useState(false); // 모달 창
  // 예약 폼 입력 값
  const [personalForm, setPersonalForm] = useState({
    name: '',
    title: '',
    location: '',
    email: '',
    time: '',
  });

  const [reservedTimes, setReservedTimes] = useState({});

  // 날짜
  const formatDate = (dateObj) => {
    const year = dateObj.getFullYear();
    const month = String(dateObj.getMonth() + 1).padStart(2, '0');
    const day = String(dateObj.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

 // 2시간 간격 시간 생성 함수
  const generateTimeSlots = () => {
    const times = [];
    for (let hour = 8; hour <= 22; hour += 2) {
      times.push(`${hour.toString().padStart(2, '0')}:00`);
    }
    return times;
  };

  const fetchReservations = async () => {
    if (!user) return;

    // 사용자 식별용
    const userId = user.id;
    const resId = res.id;
    
    // 문자열이랑 같이 쓸 때는 ' 대신 ` 쓰기
    try {
      const [acceptedRes, pendingRes, reservedRes] = await Promise.all([
        fetch(`/api/host/${user.id}/scheList`),
        fetch(`/api/host/${user.id}/reservList`),
        fetch(`/api/host/${user.id}/scheList`),
      ]);

      // acceptedRes = 사용자가 받은 예약 및 일정 목록
      const acceptedData = await acceptedRes.json();
      // pendingRes = 아직 사용자가 수락하지 않은 예약 요청 목록
      const pendingData = await pendingRes.json();
      // reservedRes = 이미 예약된 시간대 목록
      const reservedData = await reservedRes.json();

      // 받은 일정
      setAcceptedReservations(acceptedData); // { "YYYY-MM-DD": { title, location, requester } }
      // 수락한 일정
      setPendingRequests(pendingData);       // [{ date, title, location, requester }]
      // 예약된 시간
      setReservedTimes(reservedData);        // { "YYYY-MM-DD": ["10:00", "12:00"] }
    } catch (error) {
      console.error('데이터 불러오기 실패:', error);
    }
  };

  // 벡앤드에서 예약 데이터를 불러옴 (fetchReservations())
  useEffect(() => {
    fetchReservations();
} , [user]);

    // 시간 클릭 시 formData에 저장
  const handleTimeClick = (time) => {
    const dateKey = formatDate(date);
    // 이미 예약된 시간일 경우 클릭 무시
    if (reservedTimes[dateKey]?.includes(time)) return;
    setPersonalForm((prev) => ({ ...prev, time }));
  };

  // 캘린더에 일정 제목 표시
  const tileContent = ({ date, view }) => {
    if (view !== 'month') return null;
    const dateKey = formatDate(date);
    const reservation = acceptedReservations[dateKey];
    return reservation ? (
      <div style={{ fontSize: '13px', color: '#333' }}>
        {reservation.title}
      </div>
    ) : null;
  };

  // 일정 수락
  const handleAccept = async (request) => {
    try {
      const res = await fetch(`/api/reserv/${res.id}`, { 
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ...request, hostId: user.id }),
      });

      if (!res.ok) throw new Error('수락 실패');

      fetchReservations();
    } catch (err) {
      console.error(err);
      alert('❌ 수락 처리 실패');
    }
  };
 
  // 일정 거절
  const handleReject = async (request) => {
    try {
      const res = await fetch(`/api/reserv/${res.id}`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ...request, hostId: user.id }),
      });

      if (!res.ok) throw new Error('거절 실패');

      fetchReservations();
    } catch (err) {
      console.error(err);
      alert('❌ 거절 처리 실패');
    }
  };

  const handlePersonalSubmit = async (e) => {
    e.preventDefault();
    if (!user) {
      alert('❌ 로그인이 필요합니다.');
      return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(personalForm.email)) {
      alert('❌ 이메일 양식이 올바르지 않습니다.');
      return;
    }

    if (!personalForm.time) {
      alert('❌ 시간을 선택해주세요.');
      return;
    }

    const dateKey = formatDate(date);

    // 사용자가 개인 일정 추가 시 백엔드에 정보 전송
    try {
      const response = await fetch(`/api/schedule/${user.id}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          ...personalForm,
          date: dateKey,
          hostId: user.id,
        }),
      });

      if (!response.ok) throw new Error('서버 오류');

      // 기존 데이터 유지 + 사용자가 선택한 날짜(dateKey)에 일정 저장
      setAcceptedReservations((prev) => ({
        ...prev,
        [dateKey]: {
          title: personalForm.title,
          location: personalForm.location,
          requester: personalForm.name || user.nickname || '나',
        },
      }));

      // 예약 된 시간 저장 -> 시간 중복 확인
      setReservedTimes((prev) => ({
        ...prev,
        [dateKey]: [...(prev[dateKey] || []), personalForm.time],
      }));

      alert('✅ 개인 일정이 등록되었습니다!');
      setShowModal(false);
      setPersonalForm({ name: '', title: '', location: '', email: '', time: '' });
    } catch (err) {
      console.error('예약 실패:', err);
      alert('❌ 예약에 실패했습니다.');
    }
  };


  return (
    <div className="my-layout">
      <div className="mleft-section">
        <button onClick={() => setShowModal(true)} className="s-button">
          + 개인일정예약
        </button>
        <div className="calendar">
          <Calendar
            onChange={setDate}
            value={date}
            locale="en-US"
            tileContent={tileContent}
          />
        </div>
      </div>

      <div className="mright-section">
        <div className="my-reservation" style={{ marginTop: '100px' }}>
        <h2>나의 예약 일정</h2>
        <p className="date-num">날짜: {date.toLocaleDateString()}</p>
        {acceptedReservations[formatDate(date)] ? (
          <div>
            <p><strong>제목:</strong> {acceptedReservations[formatDate(date)].title}</p>
            <p><strong>위치:</strong> {acceptedReservations[formatDate(date)].location}</p>
            <p><strong>예약자:</strong> {acceptedReservations[formatDate(date)].requester}</p>
          </div>
        ) : (
          <p className="no-reservation">해당 날짜에는 일정이 없습니다.</p>
        )}

        <hr style={{ margin: '30px 0' }} />
        </div>

        <div className="pending-requests" style={{ marginTop: '50px' }}>
        <h2>예약 요청 목록</h2>
        {pendingRequests.length === 0 ? (
          <p className="no-request">요청된 일정이 없습니다.</p>
        ) : (
          pendingRequests.map((req, idx) => (
            <div key={idx} className="request-box">
              <p><strong>예약자:</strong> {req.requester}</p>
              <p><strong>제목:</strong> {req.title}</p>
              <p><strong>장소:</strong> {req.location}</p>
              <p><strong>날짜:</strong> {req.date}</p>
              <button onClick={() => handleAccept(req)} style={{ marginRight: '10px' }}>✅ 수락</button>
              <button onClick={() => handleReject(req)}>❌ 거절</button>
            </div>
          ))
        )}
        </div>
      </div>

      {showModal && (
      <Modal
        onClose={() => setShowModal(false)}
        onSubmit={handlePersonalSubmit}
        formData={personalForm}
        setFormData={setPersonalForm}
        selectedDate={date}
        reservedTimes={reservedTimes}
        handleTimeClick={handleTimeClick}
        generateTimeSlots={generateTimeSlots}
      />
    )}
    </div>
  );
}

export default MyPage;
