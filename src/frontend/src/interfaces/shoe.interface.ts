import GenericProduct from "./generic-product.interface";

interface Shoe extends GenericProduct {
  color: string;
  sizeEUR: number;
  sizeUK: number;
  sizeUS: number;
  collectionId: number;
}

export default Shoe;
