<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Inquiries – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/cars" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/cars" class="nav-link">Browse</a>
    <a href="${pageContext.request.contextPath}/inquiry/my" class="nav-link active">My Inquiries</a>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>
<div class="page-wrapper">
  <div class="page-header">
    <h1 class="page-title">My Inquiries</h1>
    <p class="page-subtitle">Track your sent inquiries and seller responses</p>
  </div>
  <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
  <c:choose>
    <c:when test="${empty inquiries}">
      <div class="card text-center" style="padding:3rem">
        <h3 class="mt-2">No inquiries yet</h3>
        <p class="text-secondary mt-1">Browse cars and send an inquiry to a seller.</p>
      </div>
    </c:when>
    <c:otherwise>
      <div style="display:grid;gap:1rem">
        <c:forEach var="inq" items="${inquiries}">
          <div class="card">
            <div class="card-body">
              <div class="flex justify-between items-center">
                <div>
                  <div class="font-bold">Inquiry #${inq.inquiryId}</div>
                  <div class="text-secondary text-sm">Car: ${inq.carId} · Submitted: ${inq.submittedDate}</div>
                </div>
                <span class="car-badge" style="background:${inq.status.name() == 'RESPONDED' ? '#dcfce7' : '#fef3c7'};color:${inq.status.name() == 'RESPONDED' ? '#15803d' : '#d97706'}">${inq.status}</span>
              </div>
              <div style="background:var(--surface-2);border-radius:var(--radius-sm);padding:1rem;margin-top:1rem">
                <div class="text-sm font-bold text-secondary mb-1">Your Message:</div>
                <p style="font-size:.9rem">${inq.message}</p>
              </div>
              <c:if test="${not empty inq.responseMessage}">
                <div style="background:#dcfce7;border-radius:var(--radius-sm);padding:1rem;margin-top:.75rem">
                  <div class="text-sm font-bold" style="color:#15803d;margin-bottom:.25rem">Seller Response (${inq.responseDate}):</div>
                  <p style="font-size:.9rem">${inq.responseMessage}</p>
                </div>
              </c:if>
              <div class="flex gap-1 mt-2">
                <a href="${pageContext.request.contextPath}/cars/${inq.carId}" class="btn btn-secondary btn-sm">View Car</a>
                <form action="${pageContext.request.contextPath}/inquiry/delete/${inq.inquiryId}" method="post" onsubmit="return confirm('Delete inquiry?')">
                  <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                </form>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
