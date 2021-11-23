import Color from "../types/Color.type";
import GenericProduct from "./generic-product.interface";

interface Shoe extends GenericProduct {
  color: Color;
  sizeEUR: number;
  sizeUK: number;
  sizeUS: number;
  collection: string;
}

export default Shoe;
