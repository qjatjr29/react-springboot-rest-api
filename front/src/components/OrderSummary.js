import React, { useEffect, useState } from "react";
import { SummaryItem } from "./SummaryItem";

export function OrderSummary({ items = [], onOrderSubmit, onOrderListSubmit }) {
  const totalPrice = items.reduce(
    (prev, curr) => prev + curr.price * curr.count,
    0
  );
  const [order, setOrder] = useState({
    name: "",
    address: "",
    phoneNumber: "",
    userId: "",
  });

  const handleNameInputChanged = (e) => {
    setOrder({ ...order, name: e.target.value });
  };
  const handleAddressInputChanged = (e) => {
    setOrder({ ...order, address: e.target.value });
  };
  const handlePhoneNumberInputChanged = (e) => {
    setOrder({ ...order, phoneNumber: e.target.value });
  };
  const handleSubmit = (e) => {
    if (order.name === "" || order.address === "" || order.phoneNumber === "") {
      alert("입력값을 확인해 주세요..!");
    } else {
      sessionStorage.getItem("user");
      onOrderSubmit(order, totalPrice);
    }
  };
  const handleListSubmit = (e) => {
    if (order.name === "" || order.address === "" || order.phoneNumber === "") {
      alert("입력값을 확인해 주세요..!");
    } else {
      onOrderListSubmit(order, totalPrice);
    }
  };

  useEffect(() => {
    setOrder({ ...order, userId: sessionStorage.getItem("user") });
  }, []);

  console.log(order);

  return (
    <>
      <div>
        <h5 className="m-0 p-0">
          <b>Order List</b>
        </h5>
      </div>
      <hr />
      {items.map((v) => (
        <SummaryItem key={v.foodId} count={v.count} name={v.name} />
      ))}
      <form>
        <div className="mb-3">
          <label for="name" className="form-label">
            이름
          </label>
          <input
            type="text"
            className="form-control mb-1"
            value={order.name}
            onChange={handleNameInputChanged}
            id="email"
          />
        </div>
        <div class="mb-3">
          <label for="address" className="form-label">
            주소
          </label>
          <input
            type="text"
            className="form-control mb-1"
            value={order.address}
            onChange={handleAddressInputChanged}
            id="address"
          />
        </div>
        <div class="mb-3">
          <label for="phoneNumber" className="form-label">
            연락처
          </label>
          <input
            type="tel"
            className="form-control"
            value={order.phoneNumber}
            onChange={handlePhoneNumberInputChanged}
            id="postcode"
          />
        </div>
      </form>
      <div className="row pt-2 pb-2 border-top">
        <h5 className="col">총금액</h5>
        <h5 className="col text-end">{totalPrice}원</h5>
      </div>
      <div class="btn-group">
        <button className="btn btn-secondary col-15" onClick={handleListSubmit}>
          장바구니 저장
        </button>
        <button className="btn btn-dark col-15" onClick={handleSubmit}>
          주문하기
        </button>
      </div>
    </>
  );
}
