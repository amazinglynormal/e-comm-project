import Product from "../types/Product.type";

const products: Product[] = [
  {
    id: 1,
    name: "Test Clothing",
    description: "test description",
    categoryId: 1,
    imageSrc: "test-image-src",
    imageAlt: "test image alt",
    eur: 9.99,
    usd: 9.99,
    gbp: 9.99,
    color: "black",
    size: "M",
    inStock: true,
  },
  {
    id: 2,
    name: "Test Clothing 2",
    description: "test description",
    categoryId: 1,
    imageSrc: "test-image-src",
    imageAlt: "test image alt",
    eur: 10.99,
    usd: 10.99,
    gbp: 10.99,
    color: "blue",
    size: "L",
    inStock: true,
  },
  {
    id: 3,
    name: "Test Clothing 3",
    description: "test description",
    categoryId: 1,
    imageSrc: "test-image-src",
    imageAlt: "test image alt",
    eur: 11.99,
    usd: 11.99,
    gbp: 11.99,
    color: "red",
    size: "S",
    inStock: true,
  },
  {
    id: 4,
    name: "Test Clothing 4",
    description: "test description",
    categoryId: 2,
    imageSrc: "test-image-src",
    imageAlt: "test image alt",
    eur: 12.99,
    usd: 12.99,
    gbp: 12.99,
    color: "white",
    size: "M",
    inStock: false,
  },
];

export default products;
