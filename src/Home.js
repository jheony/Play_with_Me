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
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    //alert 창으로 사용자가 입력한 정보 띄우기
    alert(
      `✅ 예약이 완료되었습니다!\n\n` +
      `날짜: ${date.toLocaleDateString()}\n` +
      `이름: ${formData.name}\n` +
      `일정 제목: ${formData.title}\n` +
      `위치: ${formData.location}\n` +
      `이메일: ${formData.email}` +
      '\n호스트와 예약자에게 이메일로 예약 내용을 전송할 예정입니다.'
    );

    //폼 입력 값 초기화
    setFormData({
      name: '',
      title: '',
      location: '',
      email: '',
    });
  };

  return (
    <div className="calendar-wrapper">
        <Calendar onChange={setDate} value={date} locale="en-US"/>
      <div className="form-container">
        <h2 className="form-title">일정 예약하기</h2>
          <form onSubmit={handleSubmit}>
            <label>이름</label>
            <input name="name" value={formData.name} onChange={handleChange} placeholder="홍길동" required />

            <label>일정 제목</label>
            <input name="title" value={formData.title} onChange={handleChange} placeholder="감성 카페 투어 가자!" required />

            <label>일정 위치</label>
            <input name="location" value={formData.location} onChange={handleChange} placeholder="동명동" required />

            <label>이메일</label>
            <textarea name="email" value={formData.email} onChange={handleChange} placeholder="example@email.com" required />

            <button type="submit" className="next-button">다음</button>
          </form>
      </div>
    </div>
  );
}

export default Home;
