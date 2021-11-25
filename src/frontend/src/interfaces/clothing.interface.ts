import Color from "../types/Color.type";
import GenericProduct from "./generic-product.interface";

interface Clothing extends GenericProduct {
  collection: string;
  allSizes: number[];
  availableSizes: number[];
  color: Color;
}

export default Clothing;
