import React, { useState } from 'react';
import './App.css';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

function Home() {
  const [date, setDate] = useState(new Date());

  const [formData, setFormData] = useState({
    name: '',
    title: '',
    location: '',
    email: '',
    time: '',
  });

  const [reservedTimes, setReservedTimes] = useState({
    "2025-04-25": ["10:00", "14:00", "18:00"],
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.email)) {
      alert('❌ 이메일의 양식이 올바르지 않습니다. 예: example@email.com');
      return;
    }

    const formattedDate = formatDate(date);

    // 예약 정보를 저장하고 예약된 시간에 추가
    setReservedTimes((prev) => ({
      ...prev,
      [formattedDate]: [...(prev[formattedDate] || []), formData.time],
    }));

    alert(
      `✅ 예약이 완료되었습니다!\n\n` +
      `날짜: ${date.toLocaleDateString()}\n` +
      `시간: ${formData.time}\n` +
      `이름: ${formData.name}\n` +
      `일정 제목: ${formData.title}\n` +
      `위치: ${formData.location}\n` +
      `이메일: ${formData.email}` +
      '\n호스트와 예약자에게 이메일로 예약 내용을 전송할 예정입니다.'
    );

    setFormData({
      name: '',
      title: '',
      location: '',
      email: '',
      time: '',
    });
  };

  const generateTimeSlots = () => {
    const times = [];
    for (let hour = 8; hour <= 22; hour += 2) {
      const timeStr = `${hour.toString().padStart(2, '0')}:00`;
      times.push(timeStr);
    }
    return times;
  };

  const formatDate = (dateObj) => {
    return dateObj.toISOString().split('T')[0];
  };

  const handleTimeClick = (time) => {
    const dateKey = formatDate(date);
    if (reservedTimes[dateKey]?.includes(time)) return;
    setFormData(prev => ({ ...prev, time }));
  };

  return (
    <div className="calendar-wrapper">
      <Calendar onChange={setDate} value={date} locale="en-US" />

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
                        ? '#007bff'
                        : 'white',
                      border: '1px solid #ccc',
                      padding: '20px',
                      margin: '10px',
                      borderRadius: '5px',
                      width: '80px',
                      textAlign: 'center',
                      cursor: isReserved ? 'not-allowed' : 'pointer',
                      fontSize: '18px',
                      marginTop: '45px'
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
        <h2 className="form-title">일정 예약하기</h2>
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
            placeholder="감성 카페 투어 가자!"
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
          <textarea
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="example@email.com"
            required
          />

          <button type="submit" className="next-button">다음</button>
        </form>
      </div>
    </div>
  );
}

export default Home;
