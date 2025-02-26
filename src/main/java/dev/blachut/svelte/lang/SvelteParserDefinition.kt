package dev.blachut.svelte.lang

import com.intellij.lang.ASTNode
import com.intellij.lang.LanguageUtil
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import dev.blachut.svelte.lang.psi.SvelteFile
import dev.blachut.svelte.lang.psi.SvelteTypes

class SvelteParserDefinition : ParserDefinition {
    override fun createLexer(project: Project): Lexer {
//        val level = JSRootConfiguration.getInstance(project).languageLevel
//        return SvelteLexer(if (level.isES6Compatible) level else JSLanguageLevel.ES6)

        return SvelteLexer()
    }

    override fun getWhitespaceTokens(): TokenSet {
        return WHITE_SPACES
    }

    override fun getCommentTokens(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createParser(project: Project): PsiParser {
        return SvelteParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return SvelteFile(viewProvider)
    }

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode, right: ASTNode): ParserDefinition.SpaceRequirements {
        val lexer = this.createLexer(left.psi.project)
        return LanguageUtil.canStickTokensTogetherByLexer(left, right, lexer)
    }

    override fun createElement(node: ASTNode): PsiElement {
        return SvelteTypes.Factory.createElement(node)
    }

    companion object {
        val WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE)

        val FILE = IFileElementType(SvelteLanguage.INSTANCE)
    }
}
