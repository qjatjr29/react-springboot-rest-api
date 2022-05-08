import { Link} from 'react-router-dom';

function Navbar(){
    return (<div>      <Link to ="/">
    <p>홈</p>
</Link> 
<Link to ="/foods">
    <p>음식</p>
</Link> 
<Link to ="/category">
    <p>카테고리</p>
</Link> </div>

    )
}

export default Navbar;