import Collection from "./collection.interface";

interface Category {
  id: number;
  name: string;
  description: string;
  collections: Collection[];
}

export default Category;
