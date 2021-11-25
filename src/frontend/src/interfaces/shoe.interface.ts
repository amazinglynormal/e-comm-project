import Color from "../types/Color.type";
import GenericProduct from "./generic-product.interface";

interface Shoe extends GenericProduct {
  color: Color;
  allSizes: number[];
  availableSizes: number[];
  collection: string;
}

export default Shoe;
