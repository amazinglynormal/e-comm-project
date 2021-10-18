import Product from "./product.interface";

interface Category {
  id: number;
  name: string;
  description: string;
  products: Product[];
}
export default Category;
