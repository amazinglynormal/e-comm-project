import Product from "../types/Product.type";

interface Category {
  id: number;
  name: string;
  description: string;
  collections: Product[];
}

export default Category;
