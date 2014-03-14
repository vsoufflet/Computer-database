<jsp:include page="include/header.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section id="main">
	<h1 id="homeTitle">${NombreOrdinateurs}</h1>
	<div id="actions">
		<form action="DashboardServlet" method="GET">
			<input type="search" id="searchbox" name="search"
				placeholder="Search name"> <a class="btn primary"
				id="searchsubmit" href="DashboardServlet">Filter by name</a>
		</form>
		<a class="btn success" id="add" href="addComputer.jsp">Add
			Computer</a>
	</div>

	<table class="computers zebra-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<!-- Table header for Computer Name -->
				<th>Computer Name</th>
				<th>Introduced Date</th>
				<!-- Table header for Discontinued Date -->
				<th>Discontinued Date</th>
				<!-- Table header for Company -->
				<th>Company</th>
				<th></th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="entry" items="${computerList}">
				<tr>
					<td><a href="EditComputerServlet?name=${entry.name}"><c:out
								value="${entry.name}" /></a></td>
					<td>${entry.introduced}</td>
					<td>${entry.discontinued}</td>
					<td>${entry.company.name}</td>
					<td><a class="btn danger" id="Delete"
						href="DeleteComputerServlet">Delete Computer</a></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
</section>

<jsp:include page="include/footer.jsp" />
