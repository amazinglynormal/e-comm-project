import Color from "../types/Color.type";
import GenericProduct from "./generic-product.interface";

interface Accessory extends GenericProduct {
  color: Color;
  allSizes: string[];
  availableSizes: string[];
  collection: string;
}

export default Accessory;
