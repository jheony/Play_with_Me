// 로그인 관리
import React, { createContext, useState } from 'react';

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  // 테스트용
  const [user, setUser] = useState({
    name: 'ㅇㅅㅇ',
    email: 'test@example.com',
    passwd: 'test1234'
  });


  // 실전용
  // const [user, setUser] = useState(null); // { name, email, passwd }

  const login = (userData) => setUser(userData);
  const logout = () => setUser(null);

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
