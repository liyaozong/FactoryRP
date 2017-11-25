
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
          "js/home/AuthorizationService.js",
          'app.js',
          'js/home/HttpIntercepters.js',
          'js/comm/inderService.js',
           "js/home/HomeController.js",
           "js/comm/base64.js",
           "js/comm/md5.js",

          "js/daianla-authorizationM/authorizationApp.js",
          "js/daianla-authorizationM/userManagementService.js",
          "js/daianla-authorizationM/userManagementController.js",
          "js/daianla-authorizationM/roleManageService.js",
          "js/daianla-authorizationM/roleManageController.js",
          "js/daianla-authorizationM/functionManageController.js",
          "js/daianla-authorizationM/functionManageService.js",
          "js/daianla-authorizationM/departmentManageController.js",
          "js/daianla-authorizationM/departmentManageService.js",
          "js/daianla-authorizationM/areaManagelController.js",
          "js/daianla-authorizationM/areaManagelService.js",
          "js/daianla-authorizationM/appSetService.js",
          "js/daianla-authorizationM/appSetController.js",
          "js/daianla-authorizationM/teamManageController.js",
          "js/daianla-authorizationM/teamManageService.js",
          "js/daianla-authorizationM/repairOrderModelController.js",
          "js/daianla-authorizationM/repairOrderModelService.js",

            "js/daianla-consumer-declaration/consumerDeclarationApp.js",
            "js/daianla-consumer-declaration/consumerDeclarationHadAuditController.js",
            "js/daianla-consumer-declaration/consumerDeclarationHadAuditService.js",
            "js/daianla-consumer-declaration/consumerDeclarationHadRefusedController.js",
            "js/daianla-consumer-declaration/consumerDeclarationHadRefusedService.js",
            "js/daianla-consumer-declaration/consumerDeclarationNewRegisterController.js",
            "js/daianla-consumer-declaration/consumerDeclarationNewRegisterService.js",
            "js/daianla-consumer-declaration/consumerDeclarationOrderDetailController.js",
            "js/daianla-consumer-declaration/consumerDeclarationOrderDetailService.js",
            "js/daianla-consumer-declaration/consumerDeclarationOrderGetController.js",
            "js/daianla-consumer-declaration/consumerDeclarationOrderGetService.js",
            "js/daianla-consumer-declaration/consumerDeclarationSubmitController.js",
            "js/daianla-consumer-declaration/consumerDeclarationSubmitService.js",
            "js/daianla-consumer-declaration/consumerDeclarationWaitingController.js",
            "js/daianla-consumer-declaration/consumerDeclarationWaitingService.js",
            "js/daianla-consumer-declaration/consumerDeclarationWaitingDealController.js",
            "js/daianla-consumer-declaration/consumerDeclarationWaitingDealService.js",
            "js/daianla-consumer-declaration/QueryConsumerDeclarationAllOrderController.js",
            "js/daianla-consumer-declaration/QueryConsumerDeclarationAllOrderService.js",

            "js/daianla-consumer-audit/consumerAuditApp.js",
            "js/daianla-consumer-audit/consumerAuditHadAuditController.js",
            "js/daianla-consumer-audit/consumerAuditHadAuditService.js",
            "js/daianla-consumer-audit/consumerAuditHadRefusedController.js",
            "js/daianla-consumer-audit/consumerAuditHadRefusedService.js",
            "js/daianla-consumer-audit/consumerAuditOrderDetailController.js",
            "js/daianla-consumer-audit/consumerAuditOrderDetailService.js",
            "js/daianla-consumer-audit/consumerAuditWaitingController.js",
            "js/daianla-consumer-audit/consumerAuditWaitingService.js",
            "js/daianla-consumer-audit/QueryConsumerAuditAllOrderController.js",
            "js/daianla-consumer-audit/QueryConsumerAuditAllOrderService.js",
            "js/daianla-consumer-audit/consumerAuditDataCheckController.js",
            "js/daianla-consumer-audit/consumerAuditDataCheckService.js",

            "js/daianla-consumer-toBank/consumerToBankApp.js",
            "js/daianla-consumer-toBank/consumerToBankHadSubmitListController.js",
            "js/daianla-consumer-toBank/consumerToBankHadSubmitListService.js",
            "js/daianla-consumer-toBank/consumerToBankOrderDetailController.js",
            "js/daianla-consumer-toBank/consumerToBankOrderDetailService.js",
            "js/daianla-consumer-toBank/QueryConsumerToBankAllOrderController.js",
            "js/daianla-consumer-toBank/QueryConsumerToBankAllOrderService.js",
            "js/daianla-consumer-toBank/consumerHadSendToBankListController.js",
            "js/daianla-consumer-toBank/consumerHadSendToBankListService.js",
            "js/daianla-consumer-toBank/consumerNeedSendToBankListController.js",
            "js/daianla-consumer-toBank/consumerNeedSendToBankListService.js",
            "js/daianla-consumer-toBank/consumerNeedSendToBankLoanListController.js",
            "js/daianla-consumer-toBank/consumerNeedSendToBankLoanListService.js",
            "js/daianla-consumer-toBank/consumerToBankLoanOrderDetailController.js",
            "js/daianla-consumer-toBank/consumerToBankLoanOrderDetailService.js",
            "js/daianla-consumer-toBank/consumerSendFailToBankListController.js",
            "js/daianla-consumer-toBank/consumerSendFailToBankListService.js",
            "js/daianla-consumer-toBank/consumerYoungToBankListService.js",
            "js/daianla-consumer-toBank/consumerYoungToBankListController.js",
            "js/daianla-consumer-toBank/consumerPublicLoanController.js",
            "js/daianla-consumer-toBank/consumerPublicLoanService.js",
            "js/daianla-consumer-toBank/consumerNeedSendToCCBankListController.js",
            "js/daianla-consumer-toBank/consumerNeedSendToCCBankListService.js",
            "js/daianla-consumer-toBank/commitmentOrderController.js",
            "js/daianla-consumer-toBank/commitmentOrderService.js",
            "js/daianla-consumer-toBank/commitmentOrderDetailController.js",
            "js/daianla-consumer-toBank/commitmentOrderDetailService.js",

            "js/daianla-consumer-service/consumerServiceApp.js",
            "js/daianla-consumer-service/consumerServiceHadAuditController.js",
            "js/daianla-consumer-service/consumerServiceHadAuditService.js",
            "js/daianla-consumer-service/consumerServiceHadRefusedController.js",
            "js/daianla-consumer-service/consumerServiceHadRefusedService.js",
            "js/daianla-consumer-service/consumerServiceOrderDetailController.js",
            "js/daianla-consumer-service/consumerServiceOrderDetailService.js",
            "js/daianla-consumer-service/consumerServiceOrderGetController.js",
            "js/daianla-consumer-service/consumerServiceOrderGetService.js",
            "js/daianla-consumer-service/consumerServiceWaitingController.js",
            "js/daianla-consumer-service/consumerServiceWaitingDealController.js",
            "js/daianla-consumer-service/consumerServiceWaitingDealService.js",
            "js/daianla-consumer-service/consumerServiceWaitingService.js",
            "js/daianla-consumer-service/QueryConsumerServiceAllOrderController.js",
            "js/daianla-consumer-service/QueryConsumerServiceAllOrderService.js",
            "js/daianla-consumer-service/consumerServiceOrderTrackingController.js",
            "js/daianla-consumer-service/consumerServiceOrderTrackingService.js",

            "js/daianla-consumer-manage/consumerManageApp.js",
            "js/daianla-consumer-manage/consumerManageOrderDetailController.js",
            "js/daianla-consumer-manage/consumerManageOrderDetailService.js",
            "js/daianla-consumer-manage/consumerManageSetSwitchController.js",
            "js/daianla-consumer-manage/consumerManageSetSwitchService.js",
            "js/daianla-consumer-manage/QueryConsumerManageAllOrderController.js",
            "js/daianla-consumer-manage/QueryConsumerManageAllOrderService.js",
            "js/daianla-consumer-againstCheatReport/againstCheatApp.js",
           "js/daianla-consumer-audit/againstCheatReportService.js",
          "js/daianla-consumer-audit/againstCheatReportController.js",
         "js/daianla-consumer-againstCheatReport/realNameService.js",
         "js/daianla-consumer-againstCheatReport/realNameController.js",
          "js/daianla-consumer-audit/operatorReportService.js",
         "js/daianla-consumer-audit/operatorReportController.js",

      "js/daianla-basicDataMaintain/basicDataMaintainApp.js",
      "js/daianla-basicDataMaintain/phoneLabelService.js",
      "js/daianla-basicDataMaintain/phoneLabelController.js",
      "js/daianla-basicDataMaintain/addressBlackListService.js",
      "js/daianla-basicDataMaintain/addressBlackListController.js",
      "js/daianla-basicDataMaintain/productModelSettingController.js",
      "js/daianla-basicDataMaintain/productModelSettingService.js",
      "js/daianla-basicDataMaintain/modelSettingController.js",
      "js/daianla-basicDataMaintain/modelSettingService.js",
      "js/daianla-basicDataMaintain/roleSettingController.js",
      "js/daianla-basicDataMaintain/roleSettingService.js",
      "js/daianla-basicDataMaintain/loanAppManageController.js",
      "js/daianla-basicDataMaintain/loanAppManageService.js",
      "js/daianla-basicDataMaintain/questionManageController.js",
      "js/daianla-basicDataMaintain/questionManageService.js",
      "js/daianla-basicDataMaintain/questionMenuManageController.js",
      "js/daianla-basicDataMaintain/questionMenuManageService.js",
      "js/daianla-basicDataMaintain/questionTagsManageController.js",
      "js/daianla-basicDataMaintain/questionTagsManageService.js",

      "js/daianla-consumer-marketManage/consumerMarketManageApp.js",
      "js/daianla-consumer-marketManage/consumerMarketManageController.js",
      "js/daianla-consumer-marketManage/consumerMarketManageService.js",
      "js/daianla-consumer-marketManage/consumerMarketMessageListController.js",
      "js/daianla-consumer-marketManage/consumerMarketMessageListService.js",
      "js/daianla-consumer-marketManage/consumerMarketAPPRegisterStatisticsController.js",
      "js/daianla-consumer-marketManage/consumerMarketAPPRegisterStatisticsService.js",
      "js/daianla-consumer-marketManage/consumerMarketChannelStatisticsController.js",
      "js/daianla-consumer-marketManage/consumerMarketChannelStatisticsService.js",
      "js/daianla-consumer-marketManage/consumerMarketRegisterCompareController.js",
      "js/daianla-consumer-marketManage/consumerMarketRegisterCompareService.js",

      "js/daianla-consumer-loanAfterManage/consumerLoanAfterManageApp.js",
      "js/daianla-consumer-loanAfterManage/consumerLoanAfterManageController.js",
      "js/daianla-consumer-loanAfterManage/consumerLoanAfterManageService.js",
      "js/daianla-consumer-loanAfterManage/WithholdingOrderController.js",
      "js/daianla-consumer-loanAfterManage/WithholdingOrderService.js",


       "js/daianla-consumer-marketSendMessage/marketSendMessageApp.js",
       "js/daianla-consumer-marketSendMessage/marketSendMessageService.js",
       "js/daianla-consumer-marketSendMessage/consumerMarketManageService.js",
       "js/daianla-consumer-marketSendMessage/messageGroupSendController.js",
       "js/daianla-consumer-marketSendMessage/messageReplyController.js",
       "js/daianla-consumer-marketSendMessage/messageSendRecordController.js",

       "js/daianla-consumer-message/templateMessageApp.js",
       "js/daianla-consumer-message/templateMessageController.js",
       "js/daianla-consumer-message/templateMessagerService.js",
       "js/daianla-consumer-message/selectConfigurationController.js",
       "js/daianla-consumer-message/selectConfigurationService.js",
       "js/daianla-consumer-message/selectMessageConfigurationController.js",
       "js/daianla-consumer-message/selectMessageConfigurationService.js",
       "js/daianla-consumer-message/templatePushController.js",
       "js/daianla-consumer-message/templatePushService.js",
        /*经营贷 合规 start*/
       "js/daianla-manage-compliance/manageComplianceApp.js",
       "js/daianla-manage-compliance/manageComplianceAllOrderController.js",
       "js/daianla-manage-compliance/manageComplianceAllOrderService.js",
       "js/daianla-manage-compliance/manageComplianceAllOrderDetailController.js",
       "js/daianla-manage-compliance/manageComplianceAllOrderDetailService.js",
       "js/daianla-manage-compliance/manageOrderListsController.js",
       "js/daianla-manage-compliance/manageOrderListsService.js",
       "js/daianla-manage-compliance/manageOrderListsDetailController.js",
       "js/daianla-manage-compliance/manageOrderListsDetailService.js",
      /*经营贷 合规 end*/
      /*经营贷 审批 start*/
      "js/daianla-manage-audit/manageAuditApp.js",
      "js/daianla-manage-audit/automaticCreditController.js",
      "js/daianla-manage-audit/automaticCreditService.js",
      "js/daianla-manage-audit/keyDataIndicatorsController.js",
      "js/daianla-manage-audit/keyDataIndicatorsService.js",
      "js/daianla-manage-audit/manageAuditAllOrderController.js",
      "js/daianla-manage-audit/manageAuditAllOrderService.js",
      "js/daianla-manage-audit/manageAuditListController.js",
      "js/daianla-manage-audit/manageAuditListService.js",
      "js/daianla-manage-audit/manageAuditOrderController.js",
      "js/daianla-manage-audit/manageAuditOrderService.js",
      "js/daianla-manage-audit/manageAuditOrderDetailController.js",
      "js/daianla-manage-audit/manageAuditOrderDetailService.js",

      /*经营贷 审批 end*/
      /*经营贷 推送银行 start*/
       "js/daianla-manage-pushBank/managePushBankApp.js",
       "js/daianla-manage-pushBank/managePushBankAllOrderController.js",
       "js/daianla-manage-pushBank/managePushBankAllOrderService.js",
       "js/daianla-manage-pushBank/managePushBankOrderDetailController.js",
       "js/daianla-manage-pushBank/managePushBankOrderDetailService.js"
      /*经营贷 推送银行 end*/




  ])
    .pipe(concat('app.min.js'))
    .pipe(ngAnnotate())
    .pipe(uglify())
    .pipe(gulp.dest('js'));
});
gulp.task('default',function (argument) {
     gulp.start('compress','css');
})










