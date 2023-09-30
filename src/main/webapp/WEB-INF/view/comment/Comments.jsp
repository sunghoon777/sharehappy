<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script src="/js/comment/Comments.js"></script>
<script src="/js/comment/CommentsModal.js"></script>
<link rel="stylesheet" href="/css/comment/Comments.css">
<!--sweet alert-->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<!-- fontAwesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>

<!--comments-->
<div id="comment-container" postId="${donationPostDetail.id}">
    <div class="comment-container-list">
        <c:forEach var="comment" items="${commentsPageInfo.commentSummaries}">
            <div class="comment-container my-2 px-2 py-2">
                <div class="d-flex justify-content-between mb-3">
                    <span class="nickname fw-semibold">${comment.userName}</span><span class="comment-date">${comment.date}</span>
                </div>
                <p class="fw-lighter comment-content">${comment.content}</p>
                <c:if test="${not empty comment.childCommentCount and comment.childCommentCount ne 0}">
                    <div id="${comment.commentId}">
                                    <span class="reply-view-icon">
                                        <i class="fa-solid fa-chevron-up" style="color: #10c838;"></i>
                                    </span>
                        <span class="reply-view_button">
                                        <spring:message code="replyCount.commentView" arguments="${comment.childCommentCount}"/>
                                    </span>
                        <div class="reply-container ms-3" load="false" >

                        </div>
                    </div>
                </c:if>
            </div>
        </c:forEach>
    </div>
    <!-- page List -->
    <div class="pagination-container d-flex justify-content-center">
        <ul class="pagination">
            <li class="page-item">
                <span onclick="pageClick(${commentsPageInfo.startPage-5},${donationPostDetail.id},${commentsPageInfo.startPage eq 0?false:true})" class="page-link pageButton" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                </span>
            </li>
            <c:forEach begin="${commentsPageInfo.startPage}" end="${commentsPageInfo.lastPage}" varStatus="s">
                <li class="page-item ${commentsPageInfo.currentPage eq s.index?'active':''}">
                    <span onclick="pageClick(${s.index},${donationPostDetail.id},true)" class="page-link pageButton">${s.index+1}</span>
                </li>
            </c:forEach>
            <li class="page-item">
                <span onclick="pageClick(${commentsPageInfo.lastPage+1},${donationPostDetail.id},${commentsPageInfo.lastPage-commentsPageInfo.startPage < 4?false:true})" class="page-link pageButton" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </span>
            </li>
        </ul>
    </div>
</div>

<!--comment Modal -->
<div class="modal fade" id="comment-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel"><spring:message code="header.commentModal"/></h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="mb-3">
                        <label for="comment-modal-content" class="col-form-label"><spring:message code="content.commentModal"/></label>
                        <textarea class="form-control" id="comment-modal-content"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><spring:message code="close.commentModal"/></button>
                <button type="button" id="comment-modal-submit-button" class="btn btn-success"><spring:message code="submit.commentModal"/></button>
            </div>
        </div>
    </div>
</div>
<!--reply Modal -->
<div class="modal fade" id="reply-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5"><spring:message code="header.replyModal"/></h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" id="recipientId">
                <div class="mb-3">
                    <label for="recipientName" class="col-form-label"><spring:message code="recipient.replyModal"/></label>
                    <input type="text" class="form-control" readonly id="recipientName">
                </div>
                <div class="mb-3">
                    <label for="replyMessage" class="col-form-label"><spring:message code="content.replyModal"/></label>
                    <textarea class="form-control" id="replyMessage"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><spring:message code="close.replyModal"/></button>
                <button type="button" id="replyButton" class="btn btn-success"><spring:message code="submit.replyModal"/></button>
            </div>
        </div>
    </div>
</div>


