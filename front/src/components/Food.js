import React from 'react';

export function Food(props) {
  const foodId = props.foodId;
  const name = props.name;
  const image = props.image;
  const category = props.category;
  const price = props.price;
  const handleAddBtnClicked = e => {
      props.onAddClick(foodId);
  };
  return (
    <>
      <div className="col-2"><img class="img-fluid" src={`${image}`} alt="음식 이미지" /></div>
      <div className="col">
        <div className="row text-muted">{category}</div>
        <div className="row">{name}</div>
      </div>
      <div className="col text-center price">{price}원</div>
      <div className="col text-end action">
          <button onClick={handleAddBtnClicked} className="btn btn-small btn-outline-dark" href="">담기</button>
      </div>

    </>
  );
}
