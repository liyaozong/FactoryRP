
var  gulp = require('gulp');   //common.js
var  uglify = require('gulp-uglify');//压缩JS代码的gulp插件
var ngAnnotate = require('gulp-ng-annotate');
var concat = require('gulp-concat');
var minifyCSS = require('gulp-minify-css')
gulp.task('css',function(){
	gulp.src([
          "css/comm/style.css",
          "css/comm/common.css",
					"css/comm/pagination.css",
					"css/baoDan/customsDeclaration.css",
					"css/comm/webuploader.css",
					"css/riskReport/riskControl.css",
					"css/operateReport/anasysis.css",
					"css/comm/message.css"
					])
        .pipe(concat('style.css'))
				.pipe(minifyCSS())
        .pipe(gulp.dest('css'))
				
})
gulp.task('compress', function() {
  gulp.src([
        'app.js',
        "js/home/AuthorizationService.js",
        'js/home/HttpIntercepters.js',
        "js/home/HomeController.js",
        "js/home/LoginController.js",
        "js/comm/base64.js",
        "js/comm/md5.js",
        //权限相关
        "js/daianla-authorizationM/authorizationApp.js",
        "js/daianla-authorizationM/userManagementService.js",
        "js/daianla-authorizationM/userManagementController.js",
        "js/daianla-authorizationM/roleManageService.js",
        "js/daianla-authorizationM/roleManageController.js",
        "js/daianla-authorizationM/functionManageController.js",
        "js/daianla-authorizationM/functionManageService.js",
        "js/daianla-authorizationM/departmentManageController.js",
        "js/daianla-authorizationM/departmentManageService.js",
        //工厂管理相关
        "js/factroy/factoryParameterSettingApp.js",
        "js/factroy/factoryParameterSettingService.js",
        "js/factroy/factoryParameterSettingController.js",
        "js/factroy/contactCompanySettingController.js",
        "js/factroy/deviceManageController.js",
        "js/factroy/modelToolsManageController.js",
        "js/factroy/otherOptionsSettingController.js",
        "js/factroy/sparePartsManageController.js"
  ])
    .pipe(concat('app.min.js'))
    .pipe(ngAnnotate())
    .pipe(uglify())
    .pipe(gulp.dest('js'));
});
gulp.task('default',function (argument) {
     gulp.start('compress','css');
});










