import React, { useState } from 'react';
import './styles/SignUp.css';

function SignUp() {
  const [email, setEmail] = useState('');
  const [passwd, setPasswd] = useState('');
  const [name, setName] = useState('');
  const [agree, setAgree] = useState(false);

  const validateEmail = (email) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!validateEmail(email)) {
      alert("이메일의 양식이 올바르지 않습니다.");
      return;
    }

    if (passwd.length < 8 || passwd.length > 20) { //설정한 비밀번호가 8~20자리가 아닐 때
        alert('비밀번호의 양식을 8~20자로 해주세요.');
        return;
      }

    if (!agree) { //이메일 수신 동의 checkbox 클릭 하지 않을 시
      alert('이메일 수신 동의를 해주세요.');
      return;
    }
    // 추후: DB 연결 및 전송 로직
    alert('회원가입이 완료되었습니다!');
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
          //placeholder="사용하실 별명을 입력해주세요."
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
