import React, { useContext } from 'react';
import '../styles/Header.css';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';

function Header() {
  const navigate = useNavigate();
  const { user, logout } = useContext(AuthContext);

  const handleSignInClick = () => {
    navigate('/signin');
  };

  const handleSignUpClick = () => {
    navigate('/signup');
  };

  const handleSignOut = () => {
    logout();
    navigate('/home');
  };

  const goToMypage = () => {
    navigate('/mypage');
  };

  return (
    <header className="custom-header">
      <div className="header-left">
        <a href="/home" className="maintext-link">PLAY with ME</a>
      </div>

      <div className="header-right">
        {user ? (
          <>
            <span className="welcome-text">{user.name}님 환영합니다!</span>
            <button className="header-btn" onClick={goToMypage}>MyPage</button>
            <button className="header-btn" onClick={handleSignOut}>Sign out</button>
          </>
        ) : (
          <>
            <button className="header-btn" onClick={handleSignUpClick}>Sign up</button>
            <button className="header-btn" onClick={handleSignInClick}>Sign in</button>
          </>
        )}
      </div>
    </header>
  );
}

export default Header;
