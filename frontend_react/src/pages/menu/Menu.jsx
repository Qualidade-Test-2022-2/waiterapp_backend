import { useEffect, useState } from "react";
import { Product } from "../../components";
import useMenus from "./hooks/useMenus";
import "./menu.css";

export default function Menu() {
  const { menus, loading } = useMenus()
  const [menu, setMenu] = useState(null)

  useEffect(() => {
    setMenu(menus?.[0])
  }, [menus])

  console.log(menu)

  return (
    <div className="main_layout menus">
      <div className="menus__container">
        {loading ?
          <h3 className="loading">Loading...</h3>
          :
          <div>
            <header className="menus__header">
              <h2>Menus</h2>
              <select
                className="menus__selector"
                onChange={(event) => setMenu(menus.find(({ id }) => id === parseInt(event.target.value)))}
              >
                {menus?.map((menu) => (
                  <option key={menu.id} value={menu.id}>{menu.titulo}</option>
                ))}
              </select>
            </header>

            <div className="menus__items">
              {menu?.items?.map((item) => (
                <Product product={item} />
              ))}
            </div>
          </div>
        }
      </div>
    </div>
  );
}
