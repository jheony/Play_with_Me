import React, { useState } from 'react';
import './App.css'; // 기존에 구현한 스타일 활용
import './styles/SignIn.css';

function SignIn() {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    //사용자의 이메일과 비밀번호 (로그인 정보) 비교 후 일치하지 않으면 alert창 띄우기

    //로그인 처리 로직이 들어갈 수 있음
    alert(`로그인 되었습니다.\n이메일: ${formData.email}`);
  };

  return (
    <div className="login-page">
      <div className="form-container">
      <h2 className="form-title">Sign In</h2>
        <form onSubmit={handleSubmit}>
          <label>이메일</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="example@email.com"
            required
          />

        <label>비밀번호</label>
        <input
          type="password"
          name="password"
          value={formData.password}
          onChange={handleChange}
          placeholder="Password"
          required
        />

        <button type="submit" className="next-button">로그인</button>
        <a href="/signup" className="signtext-link">회원가입</a>
      </form>
      </div>
    </div>
  );
}

export default SignIn;
