import React from "react";
import { Foods } from "./Food";

export function FoodList({ foods = [], onAddClick, store }) {
  return (
    <>
      {/* <h5 className="flex-grow-0">
        <b>음식 목록</b>
      </h5> */}
      {/* <ul className="list-group products">
        <li className="list-group-item d-flex mt-3"> */}
      {/* <Product productName={v.productName} category={v.category} price={v.price} /> */}
      <Foods foods={foods} store={store} onAddClick={onAddClick} />
      {/* </li>
      </ul> */}
    </>
  );
}
