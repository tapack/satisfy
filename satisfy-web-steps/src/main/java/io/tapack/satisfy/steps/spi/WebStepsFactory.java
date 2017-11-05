package io.tapack.satisfy.steps.spi;

import io.tapack.satisfy.pages.BaseWebPage;
import io.tapack.satisfy.steps.file.BaseFileDownloadSteps;
import io.tapack.satisfy.steps.file.BaseFileUploadSteps;
import io.tapack.satisfy.steps.page.BasePageSteps;
import io.tapack.satisfy.steps.table.BaseTableSteps;
import io.tapack.satisfy.steps.webelements.BaseCalendarSteps;
import io.tapack.satisfy.steps.webelements.BaseCheckboxSteps;
import io.tapack.satisfy.steps.webelements.BaseDragNDropSteps;
import io.tapack.satisfy.steps.webelements.BaseDropDownSteps;
import io.tapack.satisfy.steps.webelements.BasePicklistSteps;
import io.tapack.satisfy.steps.webelements.BaseRadioButtonSteps;
import io.tapack.satisfy.steps.webelements.BaseWebElementActionsSteps;
import io.tapack.satisfy.steps.webelements.BaseWebElementAssertionSteps;
import io.tapack.satisfy.steps.webelements.BaseWebElementWaitSteps;
import io.tapack.satisfy.steps.webelements.IFrameRichEditorSteps;
import org.openqa.selenium.By;

import java.util.ServiceLoader;

public class WebStepsFactory {

    private WebStepsFactory() {
    }

    public static WebPage getPage() {
        ServiceLoader<WebPage> loader = ServiceLoader.load(WebPage.class);
        for (WebPage page : loader) {
            if (page.accept()) {
                return page;
            }
        }
        return new BaseWebPage();
    }

    public static PageSteps getPageSteps() {
        ServiceLoader<PageSteps> loader = ServiceLoader.load(PageSteps.class);
        for (PageSteps steps : loader) {
            if (steps.accept()) {
                return steps;
            }
        }
        return new BasePageSteps();
    }

    public static WaitSteps getWaitSteps() {
        ServiceLoader<WaitSteps> loader = ServiceLoader.load(WaitSteps.class);
        for (WaitSteps steps : loader) {
            if (steps.accept()) {
                return steps;
            }
        }
        return new BaseWebElementWaitSteps();
    }

    public static RadioButtonSteps getRadioButtonSteps() {
        ServiceLoader<RadioButtonSteps> loader = ServiceLoader.load
                (RadioButtonSteps.class);
        for (RadioButtonSteps steps : loader) {
            if (steps.accept()) {
                return steps;
            }
        }
        return new BaseRadioButtonSteps();
    }

    public static TableSteps getTableSteps(By identity) {
        ServiceLoader<TableSteps> loader = ServiceLoader.load(TableSteps.class);
        for (TableSteps steps : loader) {
            if (steps.accept(identity)) {
                return steps;
            }
        }
        return new BaseTableSteps(identity);
    }

    public static WebElementSteps getWebElementSteps(By identity) {
        ServiceLoader<WebElementSteps> loader = ServiceLoader.load
                (WebElementSteps.class);
        for (WebElementSteps steps : loader) {
            if (steps.accept(identity)) {
                return steps;
            }
        }
        return new BaseWebElementActionsSteps(identity);
    }

    public static AssertionSteps getAssertionSteps(By identity) {
        ServiceLoader<AssertionSteps> loader = ServiceLoader.load
                (AssertionSteps.class);
        for (AssertionSteps steps : loader) {
            if (steps.accept(identity)) {
                return steps;
            }
        }
        return new BaseWebElementAssertionSteps(identity);
    }

    public static DropDownSteps getDropDownSteps(By identity) {
        ServiceLoader<DropDownSteps> loader = ServiceLoader.load
                (DropDownSteps.class);
        for (DropDownSteps steps : loader) {
            if (steps.accept(identity)) {
                return steps;
            }
        }
        return new BaseDropDownSteps(identity);
    }

    public static CalendarSteps getCalendarSteps(By identity) {
        ServiceLoader<CalendarSteps> loader = ServiceLoader.load
                (CalendarSteps.class);
        for (CalendarSteps steps : loader) {
            if (steps.accept(identity)) {
                return steps;
            }
        }
        return new BaseCalendarSteps(identity);
    }

    public static CheckboxSteps getCheckboxSteps(By identity) {
        ServiceLoader<CheckboxSteps> loader = ServiceLoader.load
                (CheckboxSteps.class);
        for (CheckboxSteps steps : loader) {
            if (steps.accept(identity)) {
                return steps;
            }
        }
        return new BaseCheckboxSteps(identity);
    }

    public static RichEditorSteps getRichEditorSteps(By identity) {
        ServiceLoader<RichEditorSteps> loader = ServiceLoader.load
                (RichEditorSteps.class);
        for (RichEditorSteps richEditorSteps : loader) {
            if (richEditorSteps.accept(identity))
                return richEditorSteps;
        }
        return new IFrameRichEditorSteps();
    }

    public static DownloadSteps getDownloadSteps(By identity) {
        ServiceLoader<DownloadSteps> loader = ServiceLoader.load
                (DownloadSteps.class);
        for (DownloadSteps downloadSteps : loader) {
            if (downloadSteps.accept(identity))
                return downloadSteps;
        }
        return new BaseFileDownloadSteps(identity);
    }

    public static UploadSteps getUploadSteps(By identity) {
        ServiceLoader<UploadSteps> loader = ServiceLoader.load(UploadSteps
                .class);
        for (UploadSteps uploadSteps : loader) {
            if (uploadSteps.accept(identity))
                return uploadSteps;
        }
        return new BaseFileUploadSteps(identity);
    }

    public static PicklistSteps getPicklistSteps(By identity) {
        ServiceLoader<PicklistSteps> loader = ServiceLoader.load
                (PicklistSteps.class);
        for (PicklistSteps steps : loader) {
            if (steps.accept(identity)) {
                return steps;
            }
        }
        return new BasePicklistSteps(identity);
    }

    public static DragNDropSteps getDragNDropSteps(By identity) {
        ServiceLoader<DragNDropSteps> loader = ServiceLoader.load
                (DragNDropSteps.class);
        for (DragNDropSteps steps : loader) {
            if (steps.accept(identity)) {
                return steps;
            }
        }
        return new BaseDragNDropSteps(identity);
    }

}
