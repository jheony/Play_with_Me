import React, { useState } from 'react';
import './App.css'; 
import './styles/SignUp.css';

function SignUp() {
  const [email, setEmail] = useState('');
  const [passwd, setPasswd] = useState('');
  const [name, setName] = useState('');
  const [agree, setAgree] = useState(false);

  // 이메일 양식 맞는지 검토
  const validateEmail = (email) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateEmail(email)) {
      alert("이메일의 양식이 올바르지 않습니다.");
      return;
    }

    if (passwd.length < 8 || passwd.length > 20) {
      alert('비밀번호의 양식을 8~20자로 해주세요.');
      return;
    }

    if (!agree) {
      alert('이메일 수신 동의를 해주세요.');
      return;
    }

    // API로 회원가입 정보 전송
    try {
      const response = await fetch('/api/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, passwd, name }),
      });

      if (!response.ok) {
        const errorData = await response.json();
        alert(`❌ 회원가입 실패: ${errorData.message || '에러가 발생했습니다.'}`);
        return;
      }

      alert('✅ 회원가입이 완료되었습니다!');
      // 성공 후 폼 입력 값 초기화
      setEmail('');
      setPasswd('');
      setName('');
      setAgree(false);

    } catch (error) {
      console.error('회원가입 오류:', error);
      alert('서버와 연결에 문제가 생겼습니다.');
    }
  };

  return (
    <div className="signup-container">
      <h2 className="signup-title">회원가입</h2>
      <form className="signup-form" onSubmit={handleSubmit}>
        <label>Email</label>
        <input
          type="email"
          placeholder="example@email.com"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <label>Password</label>
        <input
          type="password"
          placeholder="8~20자리 비밀번호"
          value={passwd}
          onChange={(e) => setPasswd(e.target.value)}
        />

        <label>별명</label>
        <textarea
          placeholder="사용하실 별명을 입력해주세요."
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <div className="checkbox-area">
          <input
            type="checkbox"
            checked={agree}
            onChange={(e) => setAgree(e.target.checked)}
          />
          <label>이메일 수신에 동의합니다.</label>
        </div>

        <button className="signnext-button" type="submit">회원가입</button>
      </form>
    </div>
  );
}

export default SignUp;