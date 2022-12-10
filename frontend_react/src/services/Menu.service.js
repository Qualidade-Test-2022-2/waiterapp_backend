import { api } from './Api.service';

async function getMenus() {
  return api.get('/cardapios');
}

const Menu = { getMenus }

export default Menu
