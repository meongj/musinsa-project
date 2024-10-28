$(document).ready(function () {
  // 브랜드 추가
  $("#brand-form").on("submit", function (e) {
    e.preventDefault();
    let brandName = $("#brand-name").val();

    $.ajax({
      url: "/api/brands",
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify({ name: brandName }),
      success: function () {
        alert("브랜드가 추가되었습니다.");
        location.reload();
      },
      error: function (xhr) {
        alert("오류: " + xhr.responseJSON.message);
      },
    });
  });

  // 상품 추가
  $("#product-form").on("submit", function (e) {
    e.preventDefault();
    let brandId = $("#brand-select").val();
    let category = $("#category-select").val();
    let price = $("#product-price").val();

    $.ajax({
      url: `/api/brands/${brandId}/products`,
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify({
        category: category,
        price: price,
      }),
      success: function () {
        alert("상품이 추가되었습니다.");
        location.reload();
      },
      error: function (xhr) {
        alert("오류: " + xhr.responseJSON.message);
      },
    });
  });

  // 가격 수정
  $(".btn-edit-price").on("click", function () {
    let brandId = $(this).data("brand-id");
    let productId = $(this).data("product-id");
    let currentPrice = $(this).data("current-price");
    let newPrice = prompt("새 가격을 입력하세요:", currentPrice);

    if (newPrice && !isNaN(newPrice)) {
      $.ajax({
        url: `/api/brands/${brandId}/products/${productId}`,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify({ price: newPrice }),
        success: function () {
          location.reload();
        },
        error: function (xhr) {
          alert("오류: " + xhr.responseJSON.message);
        },
      });
    }
  });

  // 상품 삭제
  $(".btn-delete-product").on("click", function () {
    if (!confirm("정말 삭제하시겠습니까?")) return;

    let brandId = $(this).data("brand-id");
    let productId = $(this).data("product-id");

    $.ajax({
      url: `/api/brands/${brandId}/products/${productId}`,
      method: "DELETE",
      success: function () {
        location.reload();
      },
      error: function (xhr) {
        alert("오류: " + xhr.responseJSON.message);
      },
    });
  });
});
