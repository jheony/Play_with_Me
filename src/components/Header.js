// components/Header.jsx
import React from 'react';
import '../styles/Header.css';
import { useNavigate } from 'react-router-dom';

function Header() {
  const navigate = useNavigate();

  const handleSignInClick = () => {
    if (window.location.pathname === '/signin') {
      //다시 sign in 버튼을 눌렀을 때 새로고침 됨
      navigate(0);
    } else {
      navigate('/signin');
    }
  };

  return (
    <header className="custom-header">
      <div className="left-section">
      <a href="/home" className="maintext-link">PLAY with ME</a>
      </div>
      <div className="center-section">
        
      </div>
      <div className="right-section">
      <button className="sign-in" onClick={handleSignInClick}>Sign in</button>
      </div>
    </header>
  );
};

export default Header;
