import React from 'react';

export function SummaryItem({ name, count }) {
  return (
    <div className="row">
      <h6 className="p-0">{name}<span className="badge bg-dark text-">{count}개</span></h6>
    </div>
  );
}
