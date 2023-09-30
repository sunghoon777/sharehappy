<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="comment-container-list"> <div class="comment-container my-2 px-2 py-2">
    <div class="d-flex justify-content-between mb-3">
        <span class="nickname fw-semibold">tester1</span><span class="comment-date">2023-10-11 12:23</span>
    </div>
    <p class="fw-lighter comment-content">tester1 댓글</p></div> <div class="comment-container my-2 px-2 py-2">
    <div class="d-flex justify-content-between mb-3">
        <span class="nickname fw-semibold">tester2</span><span class="comment-date">2023-10-11 12:24</span>
    </div>
    <p class="fw-lighter comment-content">tester2 댓글</p></div> <div class="comment-container my-2 px-2 py-2">
    <div class="d-flex justify-content-between mb-3">
        <span class="nickname fw-semibold">tester3</span><span class="comment-date">2023-10-11 12:25</span>
    </div>
    <p class="fw-lighter comment-content">tester3 댓글</p>  <div id="13">
                                    <span class="reply-view-icon">
                                        <i class="fa-solid fa-chevron-up" style="color: #10c838;"></i>
                                    </span>
    <span class="reply-view_button">
                                        답글 3개
                                    </span>
    <div class="reply-container ms-3" load="false" >

    </div>
</div></div> <div class="comment-container my-2 px-2 py-2">
    <div class="d-flex justify-content-between mb-3">
        <span class="nickname fw-semibold">tester4</span><span class="comment-date">2023-10-11 12:26</span>
    </div>
    <p class="fw-lighter comment-content">tester4 댓글</p>  <div id="14">
                                    <span class="reply-view-icon">
                                        <i class="fa-solid fa-chevron-up" style="color: #10c838;"></i>
                                    </span>
    <span class="reply-view_button">
                                        답글 3개
                                    </span>
    <div class="reply-container ms-3" load="false" >

    </div>
</div></div> <div class="comment-container my-2 px-2 py-2">
    <div class="d-flex justify-content-between mb-3">
        <span class="nickname fw-semibold">tester5</span><span class="comment-date">2023-10-11 12:27</span>
    </div>
    <p class="fw-lighter comment-content">tester5 댓글</p></div> <div class="comment-container my-2 px-2 py-2">
    <div class="d-flex justify-content-between mb-3">
        <span class="nickname fw-semibold">tester6</span><span class="comment-date">2023-10-11 12:28</span>
    </div>
    <p class="fw-lighter comment-content">tester6 댓글</p></div> <div class="comment-container my-2 px-2 py-2">
    <div class="d-flex justify-content-between mb-3">
        <span class="nickname fw-semibold">tester7</span><span class="comment-date">2023-10-11 12:29</span>
    </div>
    <p class="fw-lighter comment-content">tester7 댓글</p></div> <div class="comment-container my-2 px-2 py-2">
    <div class="d-flex justify-content-between mb-3">
        <span class="nickname fw-semibold">tester8</span><span class="comment-date">2023-10-11 12:30</span>
    </div>
    <p class="fw-lighter comment-content">tester8 댓글</p></div> <div class="comment-container my-2 px-2 py-2">
    <div class="d-flex justify-content-between mb-3">
        <span class="nickname fw-semibold">tester9</span><span class="comment-date">2023-10-11 12:31</span>
    </div>
    <p class="fw-lighter comment-content">tester9 댓글</p></div> <div class="comment-container my-2 px-2 py-2">
    <div class="d-flex justify-content-between mb-3">
        <span class="nickname fw-semibold">tester10</span><span class="comment-date">2023-10-11 12:32</span>
    </div>
    <p class="fw-lighter comment-content">tester10 댓글</p></div></div><div class="pagination-container d-flex justify-content-center">
    <ul class="pagination">
        <li class="page-item">
                <span onclick="pageClick(-5,undefined,false)" class="page-link pageButton" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                </span>
        </li><li class="page-item active">
        <span onclick="pageClick(0,undefined,true)" class="page-link pageButton">1</span>
    </li><li class="page-item ">
        <span onclick="pageClick(1,undefined,true)" class="page-link pageButton">2</span>
    </li><li class="page-item 2">
        <span onclick="pageClick(undefined,false,true)" class="page-link pageButton">{3}</span>
    </li>  <li class="page-item">
                <span onclick="pageClick({0},{1},{2})" class="page-link pageButton" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </span>
    </li>
    </ul>
</div>
</div>
