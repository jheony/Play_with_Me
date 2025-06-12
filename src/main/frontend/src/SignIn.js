import React, { useState, useContext } from 'react';
import { AuthContext } from './contexts/AuthContext';
import { useNavigate } from 'react-router-dom';
import './App.css';
import './styles/SignIn.css';

function SignIn() {
  const [formData, setFormData] = useState({ email: '', passwd: '' });
  const { signIn } = useContext(AuthContext); // 로그인 함수
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('/api/host/signin', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        const errorData = await response.json();
        alert(`❌ 로그인 실패: ${errorData.message || '이메일 또는 비밀번호가 잘못되었습니다.'}`);
        return;
      }

      const data = await response.json();
      alert(`✅ 로그인 성공! 환영합니다, ${data.name || formData.email}님!`);

      // ✅ 로그인 성공 → 상태 저장
      SignIn({ name: data.name, email: formData.email });

      // ✅ 홈으로 이동
      navigate('/home');

    } catch (error) {
      console.error('로그인 요청 실패:', error);
      alert('⚠️ 서버와 연결할 수 없습니다. 나중에 다시 시도해주세요.');
    }
  };

  return (
    <div className="login-page">
      <div className="signin-container">
        <h2 className="signin-title">Sign In</h2>
        <form className="signin-form" onSubmit={handleSubmit}>
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
          <a href="/signup#/signup" className="signtext-link">Sign Up</a>
        </form>
      </div>
    </div>
  );
}

export default SignIn;
