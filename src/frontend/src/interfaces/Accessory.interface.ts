import Color from "../types/Color.type";
import GenericProduct from "./generic-product.interface";

interface Accessory extends GenericProduct {
  color: Color;
  size: string;
  inStock: boolean;
}

export default Accessory;
