import Color from "../types/Color.type";
import GenericProduct from "./generic-product.interface";

interface Accessory extends GenericProduct {
  color: Color;
  allSizes: number[];
  availableSizes: number[];
  collection: string;
}

export default Accessory;
