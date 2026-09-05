document.addEventListener("DOMContentLoaded", function () {

    const cartItems = document.querySelectorAll(".cart-item");

    const cartSubtotalElement =
        document.getElementById("cartSubtotal");

    const cartTotalElement =
        document.getElementById("cartTotal");


    function formatPrice(value) {

        return "₹" + Number(value).toFixed(2);

    }


    function calculateCartTotal() {

        let total = 0;


        cartItems.forEach(function (cartItem) {

            const price =
                parseFloat(cartItem.dataset.price) || 0;

            const input =
                cartItem.querySelector(".quantity-input");

            if (!input) {
                return;
            }


            const quantity =
                parseInt(input.value) || 1;


            const itemSubtotal =
                price * quantity;


            const subtotalElement =
                cartItem.querySelector(
                    ".cart-item-subtotal strong"
                );


            if (subtotalElement) {

                subtotalElement.textContent =
                    formatPrice(itemSubtotal);

            }


            total += itemSubtotal;

        });


        if (cartSubtotalElement) {

            cartSubtotalElement.textContent =
                formatPrice(total);

        }


        if (cartTotalElement) {

            cartTotalElement.textContent =
                formatPrice(total);

        }

    }


    function saveQuantity(cartItemId, quantity) {

        const formData =
            new URLSearchParams();


        formData.append(
            "action",
            "update"
        );


        formData.append(
            "cartItemId",
            cartItemId
        );


        formData.append(
            "quantity",
            quantity
        );


        fetch(
            window.contextPath + "/cart",
            {
                method: "POST",

                headers: {
                    "Content-Type":
                        "application/x-www-form-urlencoded; charset=UTF-8"
                },

                body: formData.toString()
            }
        )
        .then(function (response) {

            if (!response.ok) {

                throw new Error(
                    "Cart update failed"
                );

            }

        })
        .catch(function (error) {

            console.error(error);

        });

    }


    function updateQuantity(input) {

        const cartItem =
            input.closest(".cart-item");


        if (!cartItem) {
            return;
        }


        const cartItemId =
            input.dataset.cartItemId;


        const maxStock =
            parseInt(input.max) || 1;


        let quantity =
            parseInt(input.value);


        if (isNaN(quantity) || quantity < 1) {

            quantity = 1;

        }


        if (quantity > maxStock) {

            quantity = maxStock;

        }


        input.value = quantity;


        calculateCartTotal();


        saveQuantity(
            cartItemId,
            quantity
        );

    }


    document
        .querySelectorAll(".increase-quantity")
        .forEach(function (button) {

            button.addEventListener(
                "click",
                function () {

                    const cartItem =
                        button.closest(".cart-item");


                    const input =
                        cartItem.querySelector(
                            ".quantity-input"
                        );


                    const maxStock =
                        parseInt(input.max) || 1;


                    let quantity =
                        parseInt(input.value) || 1;


                    if (quantity < maxStock) {

                        quantity++;

                        input.value =
                            quantity;

                        updateQuantity(input);

                    }

                }
            );

        });


    document
        .querySelectorAll(".decrease-quantity")
        .forEach(function (button) {

            button.addEventListener(
                "click",
                function () {

                    const cartItem =
                        button.closest(".cart-item");


                    const input =
                        cartItem.querySelector(
                            ".quantity-input"
                        );


                    let quantity =
                        parseInt(input.value) || 1;


                    if (quantity > 1) {

                        quantity--;

                        input.value =
                            quantity;

                        updateQuantity(input);

                    }

                }
            );

        });


    document
        .querySelectorAll(".quantity-input")
        .forEach(function (input) {

            input.addEventListener(
                "change",
                function () {

                    updateQuantity(input);

                }
            );

        });


    calculateCartTotal();

});