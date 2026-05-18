<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Purchase History – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/cars" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/cars" class="nav-link">Browse</a>
    <a href="${pageContext.request.contextPath}/purchase/history" class="nav-link active">My Purchases</a>
    <a href="${pageContext.request.contextPath}/profile" class="nav-link">Profile</a>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>
<div class="page-wrapper">
  <div class="page-header">
    <h1 class="page-title">Purchase History</h1>
    <p class="page-subtitle">Track all your vehicle purchases</p>
  </div>
  <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
  <c:choose>
    <c:when test="${empty purchases}">
      <div class="card text-center" style="padding:3rem">
        <h3 class="mt-2">No purchases yet</h3>
        <p class="text-secondary mt-1 mb-2">Browse available cars and make your first purchase.</p>
        <a href="${pageContext.request.contextPath}/cars" class="btn btn-primary">Browse Cars</a>
      </div>
    </c:when>
    <c:otherwise>
      <div class="table-wrapper">
        <table>
          <thead><tr><th>Purchase ID</th><th>Car ID</th><th>Price (LKR)</th><th>Payment</th><th>Status</th><th>Date</th><th>Actions</th></tr></thead>
          <tbody>
            <c:forEach var="p" items="${purchases}">
              <tr>
                <td class="text-sm">${p.purchaseId}</td>
                <td><a href="${pageContext.request.contextPath}/cars/${p.carId}" class="text-sm">${p.carId}</a></td>
                <td style="font-weight:700;color:var(--primary)"><fmt:formatNumber value="${p.agreedPrice}" pattern="#,###"/></td>
                <td class="text-sm">${p.paymentMethod}</td>
                <td><span class="car-badge" style="background:${p.purchaseStatus.name() == 'COMPLETED' ? '#dcfce7' : p.purchaseStatus.name() == 'CANCELLED' ? '#fee2e2' : '#fef3c7'};color:${p.purchaseStatus.name() == 'COMPLETED' ? '#15803d' : p.purchaseStatus.name() == 'CANCELLED' ? '#dc2626' : '#d97706'}">${p.purchaseStatus}</span></td>
                <td class="text-sm text-secondary">${p.purchaseDate}</td>
                <td>
                  <c:if test="${p.purchaseStatus.name() == 'PENDING'}">
                    <form action="${pageContext.request.contextPath}/purchase/cancel/${p.purchaseId}" method="post" onsubmit="return confirm('Cancel this purchase?')">
                      <button type="submit" class="btn btn-danger btn-sm">Cancel</button>
                    </form>
                  </c:if>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
