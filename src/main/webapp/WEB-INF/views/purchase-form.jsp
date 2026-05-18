<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Purchase Vehicle – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/cars" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/purchase/history" class="nav-link">My Purchases</a>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>
<div class="page-wrapper" style="max-width:650px">
  <div class="page-header">
    <h1 class="page-title">Purchase Request</h1>
    <p class="page-subtitle">Confirm your purchase details below</p>
  </div>
  <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>

  <!-- Car Summary -->
  <div class="card mb-3" style="background:var(--primary);color:#fff">
    <div class="card-body flex gap-2 items-center">
      <div style="font-size:0.875rem;font-weight:700;text-transform:uppercase;letter-spacing:0.05em;opacity:.9;width:4rem;text-align:center">Vehicle</div>
      <div>
        <div style="font-size:1.2rem;font-weight:700">${car.year} ${car.make} ${car.model}</div>
        <div style="opacity:.85">Listed Price: LKR <fmt:formatNumber value="${car.price}" pattern="#,###"/></div>
        <div style="opacity:.85">${car.bodyType} · ${car.transmission} · ${car.fuelType}</div>
      </div>
    </div>
  </div>

  <div class="card">
    <div class="card-body">
      <form action="${pageContext.request.contextPath}/purchase/submit" method="post">
        <input type="hidden" name="carId"    value="${car.carId}">
        <input type="hidden" name="sellerId" value="${car.sellerId}">
        <div class="form-group">
          <label class="form-label">Agreed Price (LKR) *</label>
          <input name="agreedPrice" type="number" class="form-control" value="${car.price}" required min="1">
          <small class="text-muted">You may negotiate a price with the seller.</small>
        </div>
        <div class="form-group">
          <label class="form-label">Payment Method *</label>
          <select name="paymentMethod" class="form-control form-select" required>
            <option value="CASH">Cash</option>
            <option value="BANK_TRANSFER">Bank Transfer</option>
            <option value="INSTALLMENT">Installment Plan</option>
          </select>
        </div>
        <div class="form-group">
          <label class="form-label">Notes (Optional)</label>
          <textarea name="notes" class="form-control" rows="3" placeholder="Any special conditions or requests..."></textarea>
        </div>
        <div class="alert alert-info">This is a purchase request. The seller will confirm and finalize the transaction.</div>
        <div class="flex gap-2">
          <button type="submit" class="btn btn-success btn-lg">Confirm Purchase Request</button>
          <a href="${pageContext.request.contextPath}/cars/${car.carId}" class="btn btn-secondary btn-lg">Cancel</a>
        </div>
      </form>
    </div>
  </div>
</div>
</body>
</html>
