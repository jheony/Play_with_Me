import React from 'react'; 

const Modal = ({
  onClose,
  onSubmit,
  formData,
  setFormData,
  selectedDate,
  reservedTimes,
  handleTimeClick,
  generateTimeSlots,
}) => {
  const formatDate = (dateObj) => {
    const year = dateObj.getFullYear();
    const month = String(dateObj.getMonth() + 1).padStart(2, '0');
    const day = String(dateObj.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  const dateKey = formatDate(selectedDate);

  return (
    <div className="modal-overlay">
      <div className="modal">
        <button onClick={onClose} className="modal-close">✖</button>
        <form onSubmit={onSubmit}>
          <label>일정 제목</label>
          <input
            name="title"
            value={formData.title}
            onChange={(e) => setFormData({ ...formData, title: e.target.value })}
            required
          />

          <label>일정 위치</label>
          <input
            name="location"
            value={formData.location}
            onChange={(e) => setFormData({ ...formData, location: e.target.value })}
            required
          />

          <label>이메일</label>
          <input
            name="email"
            value={formData.email}
            onChange={(e) => setFormData({ ...formData, email: e.target.value })}
            required
          />

          <label>시간 선택</label>
          <div className="time-slots-container" style={{ display: 'flex', flexWrap: 'wrap' }}>
            {generateTimeSlots().map((time) => {
              const isReserved = reservedTimes[dateKey]?.includes(time);
              const isSelected = formData.time === time;

              return (
                <div
                  key={time}
                  onClick={() => {
                    if (isReserved) {
                      alert('❌ 이미 예약된 시간입니다.');
                      return;
                    }
                    handleTimeClick(time);
                  }}
                  title={isReserved ? '이미 예약된 시간입니다' : '선택 가능'}
                  style={{
                    color: isReserved ? 'gray' : isSelected ? 'white' : 'black',
                    backgroundColor: isReserved
                      ? '#f8d7da'
                      : isSelected
                      ? '#94c4e0'
                      : 'white',
                    border: '1px solid #ccc',
                    borderRadius: '5px',
                    padding: '10px',
                    margin: '5px',
                    width: '70px',
                    textAlign: 'center',
                    cursor: isReserved ? 'not-allowed' : 'pointer',
                    userSelect: 'none',
                    fontWeight: 'bold',
                  }}
                >
                  {time}
                </div>
              );
            })}
          </div>

          <button className="add" type="submit" style={{ marginTop: '15px' }}>
            추가
          </button>
        </form>
      </div>
    </div>
  );
};

export default Modal;
