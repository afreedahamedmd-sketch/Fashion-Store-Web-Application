<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Products - FashionStore</title>
<link rel="stylesheet" href="<c:url value='/assets/css/style.css' />">
<link rel="stylesheet" href="<c:url value='/assets/css/products.css' />">
</head>
<body>
	<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />
	<main class="products-page">
		<div class="container">
			<div class="products-header">
				<h1>Fashion Collection</h1>
				<p>Discover the latest styles for men, women and kids.</p>
			</div>
			<section class="filter-bar">
				<div class="filter-title">
					<h2>Filters</h2>
					<a href="<c:url value='/products' />">Clear</a>
				</div>
				<form action="<c:url value='/products' />" method="get"
					class="filter-form">
					<div class="filter-control">
						<label for="categoryId">Category</label> <select id="categoryId"
							name="categoryId">
							<option value="">All Categories</option>
							<c:forEach var="category" items="${categories}">
								<option value="${category.categoryId}"
									<c:if test="${selectedCategoryId != null 
								&& selectedCategoryId.toString() == category.categoryId.toString()}">selected
								</c:if>>${category.categoryName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="filter-control">
						<label for="gender">Gender</label> <select id="gender"
							name="gender">
							<option value="">All</option>
							<option value="Men"
								<c:if test="${selectedGender == 'Men'}">selected</c:if>>Men</option>
							<option value="Women"
								<c:if test="${selectedGender == 'Women'}">selected</c:if>>Women</option>
							<option value="Kids"
								<c:if test="${selectedGender == 'Kids'}">selected</c:if>>Kids</option>
							<option value="Unisex"
								<c:if test="${selectedGender == 'Unisex'}">selected</c:if>>Unisex</option>
						</select>
					</div>
					<div class="filter-control">
						<label for="size">Size</label> <select id="size" name="size">
							<option value="">All Sizes</option>
							<option value="XS"
								<c:if test="${selectedSize == 'XS'}">selected</c:if>>XS</option>
							<option value="S"
								<c:if test="${selectedSize == 'S'}">selected</c:if>>S</option>
							<option value="M"
								<c:if test="${selectedSize == 'M'}">selected</c:if>>M</option>
							<option value="L"
								<c:if test="${selectedSize == 'L'}">selected</c:if>>L</option>
							<option value="XL"
								<c:if test="${selectedSize == 'XL'}">selected</c:if>>XL</option>
							<option value="XXL"
								<c:if test="${selectedSize == 'XXL'}">selected</c:if>>XXL</option>
							<option value="28"
								<c:if test="${selectedSize == '28'}">selected</c:if>>28</option>
							<option value="30"
								<c:if test="${selectedSize == '30'}">selected</c:if>>30</option>
							<option value="32"
								<c:if test="${selectedSize == '32'}">selected</c:if>>32</option>
							<option value="34"
								<c:if test="${selectedSize == '34'}">selected</c:if>>34</option>
							<option value="36"
								<c:if test="${selectedSize == '36'}">selected</c:if>>36</option>
							<option value="38"
								<c:if test="${selectedSize == '38'}">selected</c:if>>38</option>
						</select>
					</div>
					<div class="filter-control price-control">
						<label>Price</label>
						<div class="price-inputs">
							<input type="number" name="minPrice" placeholder="Min"
								value="${minPrice}" min="0"> <input type="number"
								name="maxPrice" placeholder="Max" value="${maxPrice}" min="0">
						</div>
					</div>
					<button type="submit" class="filter-button">Apply Filters</button>
				</form>
			</section>
			<section class="products-content">
				<div class="products-toolbar">
					<p>
						<strong>${products.size()}</strong> products found
					</p>
					<div class="sort-control">
						<label for="sort">Sort by</label>
						<form action="<c:url value='/products' />" method="get">
							<input type="hidden" name="keyword" value="${keyword}"> <input
								type="hidden" name="categoryId" value="${selectedCategoryId}">
							<input type="hidden" name="gender" value="${selectedGender}">
							<input type="hidden" name="size" value="${selectedSize}">
							<input type="hidden" name="minPrice" value="${minPrice}">
							<input type="hidden" name="maxPrice" value="${maxPrice}">
							<select id="sort" name="sort" onchange="this.form.submit()">
								<option value="newest"
									<c:if test="${empty selectedSort || selectedSort == 'newest'}">selected</c:if>>Newest</option>
								<option value="price-low"
									<c:if test="${selectedSort == 'price-low'}">selected</c:if>>Price:
									Low to High</option>
								<option value="price-high"
									<c:if test="${selectedSort == 'price-high'}">selected</c:if>>Price:
									High to Low</option>
								<option value="name"
									<c:if test="${selectedSort == 'name'}">selected</c:if>>Name</option>
							</select>
						</form>
					</div>
				</div>
				<c:choose>
					<c:when test="${not empty products}">
						<div class="product-grid">
							<c:forEach var="product" items="${products}">
								<article class="product-card">
									<a
										href="<c:url value='/product-details' />?productId=${product.productId}"
										class="product-image-link">
										<div class="product-image">
											<c:choose>
												<c:when test="${not empty primaryImages[product.productId]}">
													<img
														src="<c:url value='/${primaryImages[product.productId].imagePath}' />"
														alt="${product.productName}">
												</c:when>
												<c:otherwise>
													<span>👕</span>
												</c:otherwise>
											</c:choose>
										</div>
									</a>
									<div class="product-info">
										<p class="product-brand">${product.brand}</p>
										<h3>${product.productName}</h3>
										<p class="product-gender">${product.gender}</p>
										<div class="product-price">₹${product.price}</div>
										<a
											href="<c:url value='/product-details' />?productId=${product.productId}"
											class="product-button">View Product</a>
									</div>
								</article>
							</c:forEach>
						</div>
					</c:when>
					<c:otherwise>
						<div class="no-products">
							<h2>No products found</h2>
							<p>Try changing your search or filter options.</p>
							<a href="<c:url value='/products' />" class="product-button">View
								All Products</a>
						</div>
					</c:otherwise>
				</c:choose>
			</section>
		</div>
	</main>
	<jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>