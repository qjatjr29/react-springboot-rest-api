import React from 'react';
import { Food } from './Food';

export function FoodList({ foods = [], onAddClick }) {
  return (
    <>
      <h5 className="flex-grow-0"><b>음식 목록</b></h5>
      <ul className="list-group products">
        {foods.map(v => <li key={v.foodId} className="list-group-item d-flex mt-3">
          {/* <Product productName={v.productName} category={v.category} price={v.price} /> */}
            <Food {...v} onAddClick = {onAddClick}/>
        </li>
        )}

      </ul>
    </>
  );
}
