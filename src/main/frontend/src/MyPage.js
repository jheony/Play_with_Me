import React, { useState, useContext } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import './App.css'; // 캘린더 재사용
import './styles/MyPage.css';
import { AuthContext } from './contexts/AuthContext';

function MyPage() {
  const { user } = useContext(AuthContext);
  const [date, setDate] = useState(new Date());

  const [acceptedReservations, setAcceptedReservations] = useState({
    "2025-04-25": {
      title: "감성 카페 투어",
      location: "동명동",
      requester: "홍길동",
    },
  });

  // 아직 수락하지 않은 요청 목록
  const [pendingRequests, setPendingRequests] = useState([
    {
      date: "2025-05-28",
      title: "스터디 모임",
      location: "카페",
      requester: "김코딩",
    },
    {
      date: "2025-05-01",
      title: "공원 산책",
      location: "공원",
      requester: "이피소",
    },
  ]);

  const formatDate = (dateObj) => dateObj.toISOString().split('T')[0];

  const tileContent = ({ date, view }) => {
    if (view !== 'month') return null;
    const dateKey = formatDate(date);
    const reservation = acceptedReservations[dateKey];
    return reservation ? (
      <div style={{ fontSize: '10px', color: '#007bff' }}>
        {reservation.title}
      </div>
    ) : null;
  };

  const handleAccept = (request) => {
    const { date, title, location, requester } = request;

    // 일정에 추가
    setAcceptedReservations(prev => ({
      ...prev,
      [date]: { title, location, requester },
    }));

    // 요청 목록에서 제거
    setPendingRequests(prev => prev.filter(r => r.date !== date));
  };

  const handleReject = (request) => {
    // 요청 목록에서 제거
    setPendingRequests(prev => prev.filter(r => r.date !== request.date));
  };

  return (
    <div className="main-layout">
      <div className="left-section">
        <div className="calendar">
          <Calendar
            onChange={setDate}
            value={date}
            locale="en-US"
            tileContent={tileContent}
          />
        </div>
      </div>

      <div className="right-section">
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

        <h2>예약 요청 목록</h2>
        {pendingRequests.length === 0 ? (
          <p>요청된 일정이 없습니다.</p>
        ) : (
          pendingRequests.map((req, idx) => (
            <div
              key={idx}
              style={{
                border: '1px solid #ccc',
                borderRadius: '8px',
                padding: '10px',
                marginBottom: '10px',
              }}
            >
              <p><strong>예약자:</strong> {req.requester}</p>
              <p><strong>제목:</strong> {req.title}</p>
              <p><strong>장소:</strong> {req.location}</p>
              <p><strong>날짜:</strong> {req.date}</p>
              <button onClick={() => handleAccept(req)} style={{ marginRight: '10px' }}>
                ✅ 수락
              </button>
              <button onClick={() => handleReject(req)}>❌ 거절</button>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default MyPage;
