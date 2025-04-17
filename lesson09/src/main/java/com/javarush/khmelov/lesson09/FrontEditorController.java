package com.javarush.khmelov.lesson09;

import com.javarush.khmelov.model.editor.Editor;
import com.javarush.khmelov.service.EditorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/editors")
@SessionAttributes("editorFilter")
@AllArgsConstructor
public class FrontEditorController {
    private static final String EDITORS_VIEW = "editors/editors";
    private static final String EDITOR_VIEW = "editors/editor";
    private static final String CREATE_VIEW = "editors/create";

    private final EditorService editorService;
    private final MessageSource messageSource;

    @GetMapping
    public String getAllEditors(
            Model model,
            @SessionAttribute(required = false) Editor.In editorFilter,
            @ModelAttribute Editor.In editorFilterForm
    ) {
        if (isEmpty(editorFilter) || !isEmpty(editorFilterForm)) {
            editorFilter = editorFilterForm;
        }
        final Editor.In filter = editorFilter;
        List<Editor.Out> editors = editorService.getAll()
                .stream()
                .filter(editor -> matchesFilter(editor, filter))
                .collect(Collectors.toList());
        model.addAttribute("editors", editors);
        model.addAttribute("editorFilter", editorFilter);
        return EDITORS_VIEW;
    }

    private boolean isEmpty(Editor.In editor) {
        return editor == null || (
                editor.getLogin() == null &&
                editor.getFirstname() == null &&
                editor.getLastname() == null
        );
    }

    private boolean matchesFilter(
            Editor.Out editor,
            Editor.In filter) {
        return (filter.getLogin() == null ||
                editor.getLogin().toLowerCase()
                        .contains(filter.getLogin().toLowerCase())) &&
               (filter.getLastname() == null ||
                editor.getLastname().toLowerCase()
                        .contains(filter.getLastname().toLowerCase())) &&
               (filter.getFirstname() == null ||
                editor.getFirstname().toLowerCase()
                        .contains(filter.getFirstname().toLowerCase()));
    }

    @GetMapping("/{id}")
    public String getEditorById(@PathVariable Long id, Model model) {
        Editor.Out editor = editorService.get(id);
        if (editor != null) {
            model.addAttribute("editor", editor);
            return EDITOR_VIEW;
        }
        throw new EntityNotFoundException("Editor not found");
    }

    @GetMapping("/new")
    public String createEditorForm(Model model) {
        model.addAttribute("editor", new Editor.Out());
        return CREATE_VIEW;
    }

    @PostMapping("/new")
    public String createEditor(
            @Valid @ModelAttribute("editor") Editor.In requestTo,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return CREATE_VIEW;
        }

        Editor.Out createdEditor = editorService.create(requestTo);
        String message = messageSource.getMessage(
                "editor.created.successfully",
                null,
                LocaleContextHolder.getLocale());
        redirectAttrs.addFlashAttribute(
                "message",
                message
        );
        return "redirect:/editors";
    }

    @GetMapping("/{id}/edit")
    public String updateEditorForm(@PathVariable Long id, Model model) {
        Editor.Out editor = editorService.get(id);
        if (editor != null) {
            model.addAttribute("editor", editor);
            return CREATE_VIEW;
        }
        throw new EntityNotFoundException("Editor not found");
    }

    @PostMapping("/{id}/edit")
    public String updateEditor(@PathVariable Long id,
                               @Valid @ModelAttribute("editor") Editor.In requestTo,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            return CREATE_VIEW;
        }
        requestTo.setId(id);
        Editor.Out updatedEditor = editorService.update(requestTo);
        redirectAttrs.addFlashAttribute(
                "message",
                messageSource.getMessage("editor.updated.successfully", null, LocaleContextHolder.getLocale())
        );
        return "redirect:/editors";
    }

    @PostMapping("/{id}/delete")
    public String deleteEditor(@PathVariable Long id,
                               RedirectAttributes redirectAttrs) {
        editorService.delete(id);
        redirectAttrs.addFlashAttribute(
                "message",
                messageSource.getMessage("editor.deleted.successfully", null, LocaleContextHolder.getLocale())
        );
        return "redirect:/editors";
    }


}
