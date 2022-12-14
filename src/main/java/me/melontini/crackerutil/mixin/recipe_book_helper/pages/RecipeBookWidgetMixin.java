package me.melontini.crackerutil.mixin.recipe_book_helper.pages;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import me.melontini.crackerutil.interfaces.PaginatedRecipeBookWidget;
import me.melontini.crackerutil.util.MathStuff;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(RecipeBookWidget.class)
public abstract class RecipeBookWidgetMixin implements PaginatedRecipeBookWidget {
    @Shadow
    @Final
    protected static Identifier TEXTURE;
    @Shadow
    protected MinecraftClient client;
    @Shadow
    private int parentWidth;
    @Shadow
    private int parentHeight;
    @Shadow
    private int leftOffset;
    @Shadow
    @Final
    private List<RecipeGroupButtonWidget> tabButtons;
    @Shadow
    private ClientRecipeBook recipeBook;
    @Unique
    private int page = 0;
    @Unique
    private int pages;
    @Unique
    private ToggleButtonWidget nextPageButton;
    @Unique
    private ToggleButtonWidget prevPageButton;

    @Shadow
    public abstract boolean isOpen();

    @WrapWithCondition(at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"), method = "reset")
    private boolean cracker_util$wrap(List<Object> list, Object o) {
        if (o instanceof RecipeGroupButtonWidget widget) {
            return widget.getCategory().name().contains("_SEARCH") || widget.hasKnownRecipes(this.recipeBook);
        }
        return true;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeGroupButtonWidget;setToggled(Z)V", shift = At.Shift.BEFORE), method = "reset")
    private void cracker_util$reset(CallbackInfo ci) {
        int a = (this.parentWidth - 147) / 2 - this.leftOffset;
        int s = (this.parentHeight + 166) / 2;
        this.nextPageButton = new ToggleButtonWidget(a + 14, s, 12, 17, false);
        this.nextPageButton.setTextureUV(1, 208, 13, 18, TEXTURE);
        this.prevPageButton = new ToggleButtonWidget(a - 35, s, 12, 17, true);
        this.prevPageButton.setTextureUV(1, 208, 13, 18, TEXTURE);
        this.page = 0;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V", shift = At.Shift.BEFORE), method = "render")
    private void cracker_util$render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        renderPageText(matrices);
        this.prevPageButton.render(matrices, mouseX, mouseY, delta);
        this.nextPageButton.render(matrices, mouseX, mouseY, delta);
    }

    @Unique
    private void renderPageText(MatrixStack matrices) {
        int x = (this.parentWidth - 135) / 2 - this.leftOffset - 30;
        int y = (this.parentHeight + 169) / 2 + 3;
        int displayPage = this.page + 1;
        int displayPages = this.pages + 1;
        if (this.pages > 0) {
            String string = "" + displayPage + "/" + displayPages;
            int textLength = this.client.textRenderer.getWidth(string);
            this.client.textRenderer.draw(matrices, string, (x - textLength / 2F + 20F), y, -1);
        }
    }

    @Unique
    @Override
    public void updatePages() {
        for (RecipeGroupButtonWidget widget : this.tabButtons) {
            if (widget.getPage() == this.page) {
                RecipeBookGroup recipeBookGroup = widget.getCategory();
                if (recipeBookGroup.name().contains("_SEARCH")) {
                    widget.visible = true;
                } else if (widget.hasKnownRecipes(recipeBook)) {
                    widget.visible = true;
                    widget.checkForNewRecipes(this.client);
                }
            } else {
                widget.visible = false;
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "mouseClicked", cancellable = true)
    private void cracker_util$mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (this.client.player != null) if (this.isOpen() && !this.client.player.isSpectator()) {
            if (this.nextPageButton.mouseClicked(mouseX, mouseY, button)) {
                if (this.page <= this.pages) ++this.page;
                updatePages();
                updatePageSwitchButtons();
                cir.setReturnValue(true);
            } else if (this.prevPageButton.mouseClicked(mouseX, mouseY, button)) {
                if (this.page > 0) --this.page;
                updatePages();
                updatePageSwitchButtons();
                cir.setReturnValue(true);
            }
        }
    }

    @Unique
    @Override
    public void updatePageSwitchButtons() {
        this.nextPageButton.visible = this.pages > 0 && this.page < this.pages;
        this.prevPageButton.visible = this.pages > 0 && this.page != 0;
    }

    @Inject(at = @At("HEAD"), method = "refreshTabButtons", cancellable = true)
    private void cracker_util$refresh(CallbackInfo ci) {
        this.pages = 0;
        int wc = 0;
        int x = (this.parentWidth - 147) / 2 - this.leftOffset - 30;
        int y = (this.parentHeight - 166) / 2 + 3;
        int index = 0;

        for (RecipeGroupButtonWidget widget : this.tabButtons) {
            widget.setPage(MathStuff.fastCeil(wc / 6));
            widget.setPos(x, y + 27 * index++);
            if (index == 6) {
                index = 0;
            }
            wc++;
        }

        --wc;
        this.pages = MathStuff.fastCeil(wc / 6);
        updatePages();
        updatePageSwitchButtons();
        ci.cancel();
    }

    @Override
    public int getPage() {
        return this.page;
    }

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getPageCount() {
        return this.pages;
    }
}
