import { useEffect, useState } from "react";
import { Menu } from "../../../services";

export default function useMenus() {
  const [menus, setMenus] = useState();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Menu.getMenus()
      .then((response) => {
        setMenus(response.data);
        setLoading(false);
      })
  }, [])


  return { menus, loading };
}
