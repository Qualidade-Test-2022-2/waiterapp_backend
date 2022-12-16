import { createContext, useContext, useEffect, useReducer } from "react";
import { useAuthentication } from "./AuthContext";

const CartContext = createContext();

const itemTotalPrice = ({ quantidade, item }) => {
  return quantidade * item.preco;
}

const initialState = {
  items: [],
  totalPrice: 0
}

const reducer = (state, action) => {
  switch (action.type) {
    case "ADD_TO_CART":
      return {
        items: [...state.items, action.payload],
        totalPrice: state.totalPrice + itemTotalPrice(action.payload),
      };
      case "REMOVE_FROM_CART":
        return {
          items: state.items.filter(({ item }) => item.id !== action.payload.id),
          totalPrice: state.totalPrice - itemTotalPrice(action.payload),
        };
      case "UPDATE_ITEM": {
        const itemIndex = state.items.findIndex(({ item }) => item.id === action.payload.id);
        const item = state.items[itemIndex];
        return {
          items: [
            ...state.items.slice(0, itemIndex),
            action.payload,
            ...state.items.slice(itemIndex + 1),
          ],
          totalPrice: state.totalPrice - itemTotalPrice(item) + itemTotalPrice(action.payload),
        };
      }
      case "RESET_CART":
        return initialState;
    default:
      return state;
  }
}

export const CartProvider = ({ children }) => {
  const { authenticated } = useAuthentication();
  const [cart, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    dispatch({ type: "RESET_CART" })
  }, [authenticated])
  useEffect(() => {console.log(cart)}, [cart])


  const addToCart = (product, count) => {
    let itemOfProduct = cart.items.find(({ item }) => item.id === product.id);
    if (itemOfProduct) {
      dispatch({
        type: "UPDATE_ITEM",
        payload: {
          quantidade: count,
          item: product,
        },
      });
    }
    else {
      dispatch({ type: "ADD_TO_CART", payload: { quantidade: count, item: product } });
    }
  };

  const removeFromCart = (product, count) => {
    let itemOfProduct = cart.items.find(({ item }) => item.id === product.id);

    if (itemOfProduct.quantidade > count) {
      dispatch({
        type: "UPDATE_ITEM",
        payload: {
          quantidade: itemOfProduct.quantidade - count,
          id: product.id,
        } });
    } else {
      dispatch({ type: "REMOVE_FROM_CART", payload: product });
    }

  };

  return (
    <CartContext.Provider value={{ cart, addToCart, removeFromCart }}>
      {children}
    </CartContext.Provider>
  );
}

export const useCartContext = () => {
  const { cart, addToCart, removeFromCart } = useContext(CartContext);
  return { cart, addToCart, removeFromCart };
}
