interface GenericProduct {
  id: number;
  name: string;
  description: string;
  categoryId: number;
  imageSrc: string;
  imageAlt: string;
  eur: number;
  gbp: number;
  usd: number;
}

export default GenericProduct;
