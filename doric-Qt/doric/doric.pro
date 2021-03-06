QT += \
    quick \
    widgets

CONFIG += c++11

# The following define makes your compiler emit warnings if you use
# any Qt feature that has been marked deprecated (the exact warnings
# depend on your compiler). Refer to the documentation for the
# deprecated API to know how to port your code away from it.
DEFINES += QT_DEPRECATED_WARNINGS

# You can also make your code fail to compile if it uses deprecated APIs.
# In order to do so, uncomment the following line.
# You can also select to disable deprecated APIs only up to a certain version of Qt.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

SOURCES += \
        constant.cpp \
        context.cpp \
        driver/native_driver.cpp \
        engine/js_engine.cpp \
        main.cpp \
        native/native_bridge.cpp \
        native/native_empty.cpp \
        native/native_log.cpp \
        native/native_timer.cpp \
        plugin/shader_plugin.cpp \
        registry.cpp

RESOURCES += qml.qrc

# Additional import path used to resolve QML modules in Qt Creator's code model
QML_IMPORT_PATH =

# Additional import path used to resolve QML modules just for Qt Quick Designer
QML_DESIGNER_IMPORT_PATH =

# Default rules for deployment.
qnx: target.path = /tmp/$${TARGET}/bin
else: unix:!android: target.path = /opt/$${TARGET}/bin
!isEmpty(target.path): INSTALLS += target

HEADERS += \
    async/async_result.h \
    async/callback.h \
    constant.h \
    context.h \
    context_holder.h \
    context_manager.h \
    driver/driver.h \
    driver/native_driver.h \
    engine/js_engine.h \
    native/native_bridge.h \
    native/native_empty.h \
    native/native_log.h \
    native/native_timer.h \
    plugin/shader_plugin.h \
    registry.h \
    shader/layer.h \
    template/custom_callback.h \
    template/singleton.h \
    utility/utility.h
