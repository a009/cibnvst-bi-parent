// /** index.js By Beginner Emain:zheng_jinfan@126.com HomePage:http://www.zhengjinfan.cn */
// layui.config({
// 	base: '/assets/js/'
// }).use(['element', 'layer'], function() {
// 	var element = layui.element(),
// 		$ = layui.jquery;
// 	//iframe自适应
// 	$(window).on('resize', function() {
// 		var $content = $('.admin-nav-card .layui-tab-content');
// 		$content.height($(this).height() - 106);
// 		$content.find('iframe').each(function() {
// 			$(this).height($content.height());
// 		});
// 	}).resize();
//
// 	$('.admin-side-toggle').on('click', function() {
// 		var sideWidth = $('#admin-side').width();
// 		if(sideWidth === 200) {
// 			$('#admin-body').animate({
// 				left: '0'
// 			}); //admin-footer
// 			$('#admin-footer').animate({
// 				left: '0'
// 			});
// 			$('#admin-side').animate({
// 				width: '0'
// 			});
// 		} else {
// 			$('#admin-body').animate({
// 				left: '200px'
// 			});
// 			$('#admin-footer').animate({
// 				left: '200px'
// 			});
// 			$('#admin-side').animate({
// 				width: '200px'
// 			});
// 		}
// 	});
//
// });