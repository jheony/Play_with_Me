import React, { useState, useEffect, useContext } from 'react';
import './App.css';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import { AuthContext } from './contexts/AuthContext';
import './styles/Home.css'
import './styles/Calendar.css'

function Home() {
  const { user } = useContext(AuthContext);
  const [date, setDate] = useState(new Date());
  const [formData, setFormData] = useState({
    name: '',
    title: '',
    location: '',
    email: '',
    time: '',
  });
  const [reservedTimes, setReservedTimes] = useState({});

  const formatDate = (dateObj) => {
    const year = dateObj.getFullYear();
    const month = String(dateObj.getMonth() + 1).padStart(2, '0');
    const day = String(dateObj.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  // 예약 데이터 가져오기
  useEffect(() => {
    const fetchReservations = async () => {
      if (!user) return;

      const formattedDate = formatDate(date);

      try {
        const res = await fetch(`/api/host/${user.id}/scheList`);
        const data = await res.json();

        // 해당 날짜에 해당하는 예약만 필터링
        const todayReservations = data.filter(item => item.date === formattedDate);
        const reservedTimeArray = todayReservations.map(item => item.time);

        setReservedTimes((prev) => ({
          ...prev,
          [formattedDate]: reservedTimeArray
        }));
      } catch (err) {
        console.error('예약 데이터를 불러오는 데 실패했습니다:', err);
      }
    };

    fetchReservations();
  }, [date, user]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  // 로그인 여부 확인
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!user) {
      alert('❌ 로그인이 필요합니다.');
      return;
    }
    
    // 이메일 양식 확인
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.email)) {
      alert('❌ 이메일 양식이 올바르지 않습니다.');
      return;
    }
    
    // 시간 선택 여부 확인
    if (!formData.time) {
      alert('❌ 시간을 선택해주세요.');
      return;
    }

    // 날짜 형식으로 변환
    const formattedDate = formatDate(date);

    try {
      const response = await fetch(`/api/reserv/${user.id}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          ...formData,
          date: formattedDate,
          hostId: user.id,
        }),
      });

      if (!response.ok) {
        throw new Error('서버 응답 실패');
      }

      // 성공 후 예약 상태 업데이트
      setReservedTimes((prev) => ({
        ...prev,
        [formattedDate]: [...(prev[formattedDate] || []), formData.time],
      }));

      alert(`✅ 예약 완료!\n날짜: ${date.toLocaleDateString()}\n시간: ${formData.time}`);

      setFormData({
        name: '',
        title: '',
        location: '',
        email: '',
        time: '',
      });
    } catch (error) {
      console.error('예약 실패:', error);
      alert('❌ 예약에 실패했습니다.');
    }
  };

  const generateTimeSlots = () => {
    const times = [];
    for (let hour = 8; hour <= 22; hour += 2) {
      times.push(`${hour.toString().padStart(2, '0')}:00`);
    }
    return times;
  };

  const handleTimeClick = (time) => {
    const dateKey = formatDate(date);
    if (reservedTimes[dateKey]?.includes(time)) return;
    setFormData((prev) => ({ ...prev, time }));
  };

  return (
    <div className="main-layout">
      <div className="left-section">
        <div className="calendar">
          <Calendar onChange={setDate} value={date} locale="en-US" />
        </div>
      </div>
      <div className="right-section">
        {date && (
          <div className="time-section">
            <p className="select-time-label">예약 날짜와 시간을 선택해주세요</p>
            <div className="time-slots">
              <div className="time-slot-list">
                {generateTimeSlots().map((time) => {
                  const dateKey = formatDate(date);
                  const isReserved = reservedTimes[dateKey]?.includes(time);
                  const isSelected = formData.time === time;

                  return (
                    <div
                      key={time}
                      className="time-slot"
                      style={{
                        color: isReserved ? 'gray' : isSelected ? 'white' : 'black',
                        backgroundColor: isReserved
                          ? '#eee'
                          : isSelected
                          ? '#94c4e0'
                          : 'white',
                        border: '1px solid #ccc',
                        padding: '20px',
                        margin: '10px',
                        borderRadius: '5px',
                        width: '80px',
                        textAlign: 'center',
                        cursor: isReserved ? 'not-allowed' : 'pointer',
                        fontSize: '18px',
                      }}
                      onClick={() => handleTimeClick(time)}
                    >
                      {time}
                    </div>
                  );
                })}
              </div>
            </div>
          </div>
        )}

        <div className="form-container">
          <div className="form">
            <h2 className="form-title">일정 예약하기</h2>
            <p style={{ marginTop: '0.2rem', marginBottom: '1rem', fontWeight: 'bold', fontSize: '18px' }}>
              선택한 날짜: {formatDate(date)}
            </p>

            <form onSubmit={handleSubmit}>
              <label>이름</label>
              <input
                name="name"
                value={formData.name}
                onChange={handleChange}
                placeholder="홍길동"
                required
              />

              <label>일정 제목</label>
              <input
                name="title"
                value={formData.title}
                onChange={handleChange}
                placeholder="감성 카페 투어"
                required
              />

              <label>일정 위치</label>
              <input
                name="location"
                value={formData.location}
                onChange={handleChange}
                placeholder="동명동"
                required
              />

              <label>이메일</label>
              <input
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="example@email.com"
                required
              />

              <button type="submit" className="res-button">예약</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
