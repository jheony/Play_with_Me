import React, { useState } from 'react';
import './App.css';
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

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('/api/signin', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        const errorData = await response.json();
        alert(`❌ 로그인 실패: ${errorData.message || '이메일 또는 비밀번호가 잘못되었습니다.'}`);
        return;
      }

      const data = await response.json();
      alert(`✅ 로그인 성공! 환영합니다, ${data.nickname || formData.email}님!`);

      // 로그인 성공 후 홈으로 이동
      window.location.href = "/home";

    } catch (error) {
      console.error('로그인 요청 실패:', error);
      alert('⚠️ 서버와 연결할 수 없습니다. 나중에 다시 시도해주세요.');
    }
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
