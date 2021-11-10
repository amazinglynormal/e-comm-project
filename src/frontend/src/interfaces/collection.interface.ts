import Product from "../types/Product.type";

interface Collection {
  id: number;
  name: string;
  description: string;
  products: Product[];
}

export default Collection;
