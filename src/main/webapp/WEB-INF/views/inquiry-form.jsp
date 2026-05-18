<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Submit Inquiry – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/cars" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/inquiry/my" class="nav-link">My Inquiries</a>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>
<div class="page-wrapper" style="max-width:700px">
  <div class="page-header">
    <h1 class="page-title">Send an Inquiry</h1>
    <p class="page-subtitle">Contact the seller about: <strong>${car.year} ${car.make} ${car.model}</strong></p>
  </div>
  <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>

  <div class="card mb-3" style="background:var(--surface-2)">
    <div class="card-body flex gap-2 items-center">
      <div style="font-size:0.75rem;font-weight:700;text-transform:uppercase;letter-spacing:0.05em;color:var(--text-muted);width:4rem;text-align:center">Listing</div>
      <div>
        <div class="font-bold">${car.year} ${car.make} ${car.model}</div>
        <div class="text-secondary text-sm">LKR <fmt:formatNumber value="${car.price}" pattern="#,###"/> · ${car.bodyType} · ${car.condition}</div>
      </div>
    </div>
  </div>

  <div class="card">
    <div class="card-body">
      <form action="${pageContext.request.contextPath}/inquiry/submit" method="post">
        <input type="hidden" name="carId"    value="${car.carId}">
        <input type="hidden" name="sellerId" value="${car.sellerId}">
        <div class="form-row">
          <div class="form-group">
            <label class="form-label">Your Email *</label>
            <input name="contactEmail" type="email" class="form-control" value="${loggedUser.email}" required>
          </div>
          <div class="form-group">
            <label class="form-label">Your Phone *</label>
            <input name="contactPhone" type="text" class="form-control" value="${loggedUser.phoneNumber}" required>
          </div>
        </div>
        <div class="form-group">
          <label class="form-label">Message *</label>
          <textarea name="message" class="form-control" rows="6" placeholder="Hi, I'm interested in your car. Could you tell me more about its service history and any recent repairs?" required style="resize:vertical"></textarea>
        </div>
        <div class="flex gap-2">
          <button type="submit" class="btn btn-primary">Send Inquiry</button>
          <a href="${pageContext.request.contextPath}/cars/${car.carId}" class="btn btn-secondary">Cancel</a>
        </div>
      </form>
    </div>
  </div>
</div>
</body>
</html>
