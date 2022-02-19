import { Dispatch, SetStateAction } from "react";
import { Popover } from "@headlessui/react";
import { MenuIcon } from "@heroicons/react/outline";

import { FlyoutMenu } from "./FlyoutMenu";
import { CartLink } from "./CartLink";
import { Link } from "react-router-dom";

const navigation = {
  categories: [
    {
      name: "Clothing",
      featured: [
        {
          name: "T-shirts",
          href: "/products/clothing/tshirts",
          imageSrc: "/images/tshirtcollection.jpg",
          imageAlt: "Assorted folded t-shirts on a wooden table",
        },
        {
          name: "Jeans",
          href: "/products/clothing/jeans",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-category-02.jpg",
          imageAlt:
            "Close up of Basic Tee fall bundle with off-white, ochre, olive, and black tees.",
        },
        {
          name: "Hoodies",
          href: "/products/clothing/hoodies",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-category-03.jpg",
          imageAlt:
            "Model wearing minimalist watch with black wristband and white watch face.",
        },
        {
          name: "Jackets",
          href: "/products/clothing/jackets",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-category-04.jpg",
          imageAlt:
            "Model opening tan leather long wallet with credit card pockets and cash pouch.",
        },
      ],
    },
    {
      name: "Footwear",
      featured: [
        {
          name: "Boots",
          href: "/products/footwear/boots",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-01-men-category-01.jpg",
          imageAlt:
            "Hats and sweaters on wood shelves next to various colors of t-shirts on hangers.",
        },
        {
          name: "Trainers",
          href: "/products/footwear/trainers",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-01-men-category-02.jpg",
          imageAlt: "Model wearing light heather gray t-shirt.",
        },
        {
          name: "Shoes",
          href: "/products/footwear/shoes",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-01-men-category-03.jpg",
          imageAlt:
            "Grey 6-panel baseball hat with black brim, black mountain graphic on front, and light heather gray body.",
        },
        {
          name: "Slippers",
          href: "/products/footwear/slippers",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-01-men-category-04.jpg",
          imageAlt:
            "Model putting folded cash into slim card holder olive leather wallet with hand stitching.",
        },
      ],
    },
    {
      name: "Accessories",
      featured: [
        {
          name: "Belts",
          href: "/products/accessories/belts",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-01-men-category-01.jpg",
          imageAlt:
            "Hats and sweaters on wood shelves next to various colors of t-shirts on hangers.",
        },
        {
          name: "Sunglasses",
          href: "/products/accessories/sunglasses",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-01-men-category-02.jpg",
          imageAlt: "Model wearing light heather gray t-shirt.",
        },
        {
          name: "Ties",
          href: "/products/accessories/ties",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-01-men-category-03.jpg",
          imageAlt:
            "Grey 6-panel baseball hat with black brim, black mountain graphic on front, and light heather gray body.",
        },
        {
          name: "Wallets",
          href: "/products/accessories/wallets",
          imageSrc:
            "https://tailwindui.com/img/ecommerce-images/mega-menu-01-men-category-04.jpg",
          imageAlt:
            "Model putting folded cash into slim card holder olive leather wallet with hand stitching.",
        },
      ],
    },
  ],
};

interface Props {
  setOpen: Dispatch<SetStateAction<boolean>>;
}

export const DesktopNavigation = ({ setOpen }: Props) => {
  return (
    <div className="bg-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="border-b border-gray-200">
          <div className="h-16 flex items-center justify-between">
            {/* Logo (lg+) */}
            <div className="hidden lg:flex-1 lg:flex lg:items-center">
              <Link to="/">
                <span className="sr-only">Workflow</span>
                <img
                  className="h-8 w-auto"
                  src="https://tailwindui.com/img/logos/workflow-mark.svg?color=indigo&shade=600"
                  alt=""
                />
              </Link>
            </div>

            <div className="hidden h-full lg:flex">
              {/* Flyout menus */}
              <Popover.Group className="px-4 bottom-0 inset-x-0 z-10">
                <div className="h-full flex justify-center space-x-8">
                  {navigation.categories.map((category) => (
                    <FlyoutMenu key={category.name} category={category} />
                  ))}
                </div>
              </Popover.Group>
            </div>

            {/* Mobile menu and search (lg-) */}
            <div className="flex-1 flex items-center lg:hidden">
              <button
                type="button"
                className="-ml-2 bg-white p-2 rounded-md text-gray-400"
                onClick={() => setOpen(true)}
              >
                <span className="sr-only">Open menu</span>
                <MenuIcon className="h-6 w-6" aria-hidden="true" />
              </button>
            </div>

            {/* Logo (lg-) */}
            <Link to="/" className="lg:hidden">
              <span className="sr-only">Workflow</span>
              <img
                src="https://tailwindui.com/img/logos/workflow-mark.svg?color=indigo&shade=600"
                alt=""
                className="h-8 w-auto"
              />
            </Link>

            <div className="flex-1 flex items-center justify-end">
              <div className="flex items-center lg:ml-8">
                <CartLink />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
